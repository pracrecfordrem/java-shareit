package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        Long itemId = booking.getItem() == null ? null : booking.getItem().getId();
        Long bookerId = booking.getBooker() == null ? null : booking.getBooker().getId();
        Status status = switch (booking.getStatus()) {
            case "WAITING" -> Status.WAITING;
            case "APPROVED" -> Status.APPROVED;
            default -> Status.REJECTED;
        };
        return new BookingDto(
                booking.getId(),
                String.valueOf(booking.getStart()),
                String.valueOf(booking.getEnd()),
                itemId,
                bookerId,
                status
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
        String status = switch (bookingDto.getStatus()) {
            case Status.WAITING -> "WAITING";
            case Status.APPROVED -> "APPROVED";
            default -> "REJECTED";
        };
        return new Booking(
                bookingDto.getId(),
                startDate,
                endDate,
                item,
                booker,
                status
        );
    }
}
