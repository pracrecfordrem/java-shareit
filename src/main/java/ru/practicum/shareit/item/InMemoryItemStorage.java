package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ForbiddenException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Repository
@AllArgsConstructor
public class InMemoryItemStorage {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final HashMap<Long, Item> items = new HashMap<>();

    public Item addItem(Item item) {
        if (item.getAvailable() == null) {
            throw new ValidationException("Статус должен быть указан");
        } else if (item.getName() == null || item.getName().isEmpty()) {
            throw new ValidationException("Наименование предмета должно быть указано");
        } else if (item.getDescription() == null || item.getDescription().isEmpty()) {
            throw new ValidationException("Описание предмета должно быть указано");
        }
        inMemoryUserStorage.getUser(item.getOwnerId());
        item.setId(getId());
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(ItemDto itemDto, Long userId, Long itemId) {
        if (!userId.equals(getItem(itemId).getOwnerId())) {
            throw new ForbiddenException("Только владелец предмета имеет право его редактировать");
        }
        Item newItem = ItemMapper.toItem(itemDto,userId,itemId);
        items.put(itemId,newItem);
        return newItem;
    }

    public Item getItem(long itemId) {
        return items.get(itemId);
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public Long getId() {
        return items.values()
                .stream()
                .mapToLong(Item::getId)
                .max()
                .orElse(0) + 1;
    }

    public Collection<ItemDto> getItemsBySearch(String searchText) {
        Collection<ItemDto> res = new ArrayList<>();
        if (searchText.isEmpty()) {
            return res;
        }
        for (Long key: items.keySet()) {
            if (((items.get(key).getName() != null && items.get(key).getName().toLowerCase().contains(searchText.toLowerCase()))
                    || (items.get(key).getDescription() != null && items.get(key).getDescription().toLowerCase().contains(searchText.toLowerCase())))
                    && items.get(key).getAvailable()) {
                res.add(ItemMapper.toItemDto(items.get(key)));
            }
        }
        return res;
    }
}
