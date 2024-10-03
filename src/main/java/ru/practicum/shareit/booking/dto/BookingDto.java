package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import java.time.Instant;

@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long itemId;
    private Long bookerId;
    private Status status = Status.WAITING;

    public BookingDto() {
    }
}
