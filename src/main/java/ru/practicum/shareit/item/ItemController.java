package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item addItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                        @RequestBody ItemDto itemDto) {
        System.out.println(itemDto + " " + userId);
        return itemService.addItem(itemDto,userId);
    }

    @PatchMapping
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemDto,userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable("itemId") long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.getItems(ownerId);
    }
}
