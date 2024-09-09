package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private long id;
    @NotEmpty(message = "Наименование предмета не должно быть пустым")
    private String name;
    @NotEmpty(message = "Описание предмета не должно быть пустым)")
    private String description;
    @NotNull(message = "Статус доступности предмета не должно быть пустым)")
    private Boolean available;
    private ItemRequest request;

    public ItemDto(long id, String name, String description, Boolean available, ItemRequest request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.request = request;
    }
}
