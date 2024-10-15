package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
    private Long ownerId;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;


    public ItemDto(Long id, String name, String description, Boolean available, Long requestId, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
        this.ownerId = ownerId;
    }

}
