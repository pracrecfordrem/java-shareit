package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private long id;
    private String name;
    private String description;
    private boolean isAvailable;
    private ItemRequest request;

    public ItemDto(long id, String name, String description, boolean isAvailable, ItemRequest request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isAvailable = isAvailable;
        this.request = request;
    }
}
