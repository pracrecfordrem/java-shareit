package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;


public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest().getId(),
                item.getUser().getId()
        );
    }

    public static Item toItem(ItemDto item, ItemRequest request, User user) {
        return new Item(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                user,
                request
        );
    }

//    public static Item toItem(ItemDto item, Long userId) {
//        return new Item(
//                item.getId(),
//                item.getName(),
//                item.getDescription(),
//                item.getAvailable(),
//                userId,
//                item.getRequest()
//        );
//    }
}
