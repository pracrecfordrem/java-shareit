package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;

public class InMemoryItemStorage {
    private final HashMap<Long, Item> items = new HashMap<>();
    public Item addItem(Item item) {
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
