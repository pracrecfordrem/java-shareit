package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.InMemoryUserStorage;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;

@Repository
@AllArgsConstructor
public class InMemoryItemStorage {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final HashMap<Long, Item> items = new HashMap<>();
    public Item addItem(Item item) {
        System.out.println("hereee");
//        if (true) {
//            throw new NotFoundException("Пользователь не найден");
//        }
        inMemoryUserStorage.getUser(item.getOwnerId());
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    public Item getItem(long itemId) {
        return items.get(itemId);
    }

    public Collection<Item> getItems() {
        return items.values();
    }
}
