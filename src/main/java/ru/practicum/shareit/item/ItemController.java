package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    ItemService itemService = new ItemServiceImpl();

    @PostMapping
    public Item addItem(@RequestHeader("X-Later-User-Id") Long userId,
                        @RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto,userId);
    }

    @PatchMapping
    public Item updateItem(@RequestHeader("X-Later-User-Id") Long userId,
                           @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemDto,userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto addLike(@PathVariable("itemId") long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Later-User-Id") Long ownerId) {
        return itemService.getItems(ownerId);
    }
}
