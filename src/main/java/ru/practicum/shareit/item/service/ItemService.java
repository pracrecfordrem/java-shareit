package ru.practicum.shareit.item.service;


import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Item addItem(ItemDto itemDto, Long userId);

    Item updateItem(ItemDto itemDto, Long userId, Long itemId);

    ItemDto getItem(Long userId, Long itemId);

    Collection<ItemDto> getItems(Long ownerId);

    Collection<ItemDto> getAllItems();

    Collection<ItemDto> getItemsBySearch(String searchText);

    CommentDto postComment(Long userId, Long itemId, CommentDto commentDto);
}
