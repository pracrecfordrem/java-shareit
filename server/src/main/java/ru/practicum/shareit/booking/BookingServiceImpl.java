package ru.practicum.shareit.booking;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.ForbiddenException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public Booking addBooking(BookingDto bookingDto, Long userId) {
        User booker = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new NotFoundException("Item not found"));
        List<Booking> bookingList = bookingRepository.findAllItemBookings(bookingDto.getItemId(),LocalDateTime.parse(bookingDto.getStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (!bookingList.isEmpty()) {
            throw new ValidationException("Booking with requested dates of use already exists");
        }
        if (item.getAvailable() == null || item.getAvailable()) {
            item.setAvailable(false);
            itemRepository.save(item);
        } else {
            throw new ValidationException("Attempt to book unavailable item");
        }
        return bookingRepository.save(BookingMapper.toBooking(bookingDto,item,booker));
    }

    @Transactional
    public Booking answerBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        User user = bookingRepository.findUserByBooking(bookingId);
        if (!user.getId().equals(userId)) {
            throw new ForbiddenException("Only an owner of requested item can approve or disapprove booking");
        } else if (booking.getStatus().equals("REJECTED") || booking.getStatus().equals("APPROVED")) {
            throw new ValidationException("Attempt to approve already approved or rejected booking");
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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        LocalDateTime max = LocalDateTime.of(4999,12,31, 0,0);
        LocalDateTime min = LocalDateTime.of(1900,1,1,0,0);
        return switch (state) {
            case "REJECTED" -> bookingRepository.findAllByBookerAndState(userId,"REJECTED", max,min).stream().sorted().toList();
            case "WAITING" -> bookingRepository.findAllByBookerAndState(userId, "WAITING", max,min).stream().sorted().toList();
            case "CURRENT" -> bookingRepository.findAllByBookerAndState(userId, "", LocalDateTime.now(),LocalDateTime.now()).stream().sorted().toList();
            case "FUTURE" -> bookingRepository.findAllByBookerAndStateFuture(userId).stream().sorted().toList();
            case "PAST" -> bookingRepository.findAllByBookerAndStatePast(userId).stream().sorted().toList();
            default -> bookingRepository.findAllByBookerAndState(userId, "", max,min).stream().sorted().toList();
        };
    }

    public List<Booking> getBookingByOwner(Long userId, String state) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        LocalDateTime max = LocalDateTime.of(4999,12,31, 0,0);
        LocalDateTime min = LocalDateTime.of(1900,1,1,0,0);
        return switch (state) {
            case "REJECTED" -> bookingRepository.findAllByOwnerAndState(userId,"REJECTED", max,min).stream().sorted().toList();
            case "WAITING" -> bookingRepository.findAllByOwnerAndState(userId, "WAITING", max,min).stream().sorted().toList();
            case "CURRENT" -> bookingRepository.findAllByOwnerAndState(userId, "", LocalDateTime.now(),LocalDateTime.now()).stream().sorted().toList();
            case "FUTURE" -> bookingRepository.findAllByOwnerAndStateFuture(userId).stream().sorted().toList();
            case "PAST" -> bookingRepository.findAllByOwnerAndStatePast(userId).stream().sorted().toList();
            default -> bookingRepository.findAllByOwnerAndState(userId, "", max,min).stream().sorted().toList();
        };
    }
}
