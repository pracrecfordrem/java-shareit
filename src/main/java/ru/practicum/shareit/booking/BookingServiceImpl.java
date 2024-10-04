package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.ForbiddenException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
public class BookingServiceImpl {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public Booking addBooking(BookingDto bookingDto, Long userId) {
        User booker = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        if (item.getAvailable() == null || item.getAvailable()) {
            item.setAvailable(false);
            itemRepository.save(item);
        } else {
            throw new ValidationException("Attempt to book unavailable item");
        }
        return bookingRepository.save(BookingMapper.toBooking(bookingDto,item,booker));
    }

    public Booking answerBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        User user = bookingRepository.findUserByBooking(bookingId);
        if (!user.getId().equals(userId)) {
            throw new ForbiddenException("Only an owner of requested item can approve or dissaprove booking");
        }
        booking.setStatus(approved ? "APPROVED" : "REJECTED");
        return bookingRepository.save(booking);
    }

    public Booking getBookingByOwnerOrBookingAuthor(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        User user = bookingRepository.findUserByBooking(bookingId);
        if (!user.getId().equals(userId) && !booking.getBooker().getId().equals(userId)) {
            throw new ForbiddenException("Only an owner of requested item or an author of booking can see requested booking");
        }
        return booking;
    }

    public List<Booking> getBookingByBookingAuthor(Long userId, String state) {
        User booker = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return switch (state) {
            case "WAITING" -> bookingRepository.findAllByBookerAndStateWaiting(userId).stream().sorted().toList();
            case "REJECTED" -> bookingRepository.findAllByBookerAndStateRejected(userId).stream().sorted().toList();
            case "CURRENT" -> bookingRepository.findAllByBookerAndStateCurrent(userId).stream().sorted().toList();
            case "FUTURE" -> bookingRepository.findAllByBookerAndStateFuture(userId).stream().sorted().toList();
            case "PAST" -> bookingRepository.findAllByBookerAndStatePast(userId).stream().sorted().toList();
            default -> bookingRepository.findAllByBookerAndStateAll(userId).stream().sorted().toList();
        };
    }
}
