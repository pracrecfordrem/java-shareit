package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDto {
    private Long id;
    @NotEmpty(message = "Наименование предмета не должно быть пустым")
    private String name;
    @NotEmpty(message = "Описание предмета не должно быть пустым)")
    private String description;
    @NotNull(message = "Статус доступности предмета не должно быть пустым)")
    private Boolean available;
    private Long requestId;
    private Long ownerId;
}
