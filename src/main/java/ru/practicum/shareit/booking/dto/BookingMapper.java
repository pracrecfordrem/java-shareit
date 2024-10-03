package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        Long itemId = booking.getItem() == null ? null : booking.getItem().getId();
        Long bookerId = booking.getBooker() == null ? null : booking.getBooker().getId();
        return new BookingDto(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                itemId,
                bookerId,
                booking.getStatus()
        );
    }
    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStartDate(),
                bookingDto.getEndDate(),
                item,
                booker,
                bookingDto.getStatus()
        );
    }
}
