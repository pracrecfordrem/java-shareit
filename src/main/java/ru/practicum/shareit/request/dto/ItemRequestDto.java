package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private String created;
    private List<Item> items;
}
