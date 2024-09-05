package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public interface ItemService {
    Item addItem(ItemDto itemDto, Long userId);

    Item updateItem(ItemDto itemDto, Long userId);

    ItemDto getItem(long itemId);

    List<ItemDto> getItems(Long ownerId);
}
