package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        Long itemId = booking.getItem() == null ? null : booking.getItem().getId();
        Long bookerId = booking.getBooker() == null ? null : booking.getBooker().getId();
        return new BookingDto(
                booking.getId(),
                String.valueOf(booking.getStart()),
                String.valueOf(booking.getEnd()),
                itemId,
                bookerId,
                booking.getStatus()
        );
    }

    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        if (bookingDto.getStart() != null) {
            startDate = LocalDateTime.parse(bookingDto.getStart(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            startDate = null;
        }
        if (bookingDto.getEnd() != null) {
            endDate = LocalDateTime.parse(bookingDto.getEnd(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else {
            endDate = null;
        }
        return new Booking(
                bookingDto.getId(),
                startDate,
                endDate,
                item,
                booker,
                bookingDto.getStatus()
        );
    }
}
