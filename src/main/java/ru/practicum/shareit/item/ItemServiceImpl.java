package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final InMemoryItemStorage inMemoryItemStorage;

    @Override
    public Item addItem(ItemDto itemDto, Long userId) {
        System.out.println("here");
        return inMemoryItemStorage.addItem(ItemMapper.toItem(itemDto,userId));
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long userId) {
        return inMemoryItemStorage.updateItem(ItemMapper.toItem(itemDto,userId));
    }

    @Override
    public ItemDto getItem(long itemId) {
        return ItemMapper.toItemDto(inMemoryItemStorage.getItem(itemId));
    }

    @Override
    public List<ItemDto> getItems(Long ownerId) {
        List<ItemDto> res = new ArrayList<>();
        for (Item item: inMemoryItemStorage.getItems()) {
            if (item.getOwnerId() == ownerId) {
                res.add(ItemMapper.toItemDto(item));
            }
        }
        return res;
    }


}
