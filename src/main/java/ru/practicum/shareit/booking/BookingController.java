package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking addBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto,userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking answerBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId, @RequestParam Boolean approved) {
        return bookingService.answerBooking(bookingId,approved,userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingByOwnerOrBookingAuthor(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingByOwnerOrBookingAuthor(userId,bookingId);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(defaultValue = "ALL",required = false) String state) {
        return bookingService.getBookingByOwner(userId,state);
    }

    @GetMapping
    public List<Booking> getBookingByBookingAuthor(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(defaultValue = "ALL",required = false) String state) {
        return bookingService.getBookingByBookingAuthor(userId,state);
    }
}