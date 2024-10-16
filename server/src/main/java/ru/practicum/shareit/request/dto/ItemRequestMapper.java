package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRequestMapper {

    public static ItemRequest toItemRequest(String itemDescription, User user) {
        return new ItemRequest(
                LocalDateTime.now(),
                itemDescription,
                user
        );
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<Item> itemList) {
        return new ItemRequestDto(
            itemRequest.getId(),
            itemRequest.getDescription(),
            itemRequest.getCreated().toString(),
            itemList
        );
    }
}
