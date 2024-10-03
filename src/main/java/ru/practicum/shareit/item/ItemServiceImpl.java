package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public Item addItem(ItemDto itemDto, Long userId) {
        ItemRequest request;
        if (itemDto.getRequestId() == null) {
            request = null;
        } else {
            request = itemRequestRepository.findById(itemDto.getRequestId()).get();
        }
        User owner = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRepository.save(ItemMapper.toItem(itemDto,request,owner));
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long userId, Long itemId) {
        ItemRequest request;
        if (itemDto.getRequestId() == null) {
            request = null;
        } else {
            request = itemRequestRepository.findById(itemDto.getRequestId()).get();
        }
        User owner = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRepository.save(ItemMapper.toItem(itemDto,request,owner));//inMemoryItemStorage.updateItem(itemDto,userId,itemId);
    }

    @Override
    public ItemDto getItem(Long itemId) {
        return ItemMapper.toItemDto(itemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Item not found")));//ItemMapper.toItemDto(inMemoryItemStorage.getItem(itemId));
    }

    @Override
    public Collection<ItemDto> getItems(Long ownerId) {
        List<ItemDto> res = new ArrayList<>();
        for (Item item: itemRepository.getAllByOwnerId(ownerId)) {
            res.add(ItemMapper.toItemDto(item));
        }
        return res;
    }

    @Override
    public Collection<ItemDto> getAllItems() {
        List<ItemDto> res = new ArrayList<>();
        return res;
    }

    @Override
    public Collection<ItemDto> getItemsBySearch(String searchText) {
        List<ItemDto> res = new ArrayList<>();
        if (!searchText.isEmpty()) {
            for (Item item: itemRepository.findByNameContainingIgnoreCase(searchText)) {
                if (item.getAvailable() != null && item.getAvailable()) {
                    res.add(ItemMapper.toItemDto(item));
                }
            }
        }
        return res;
    }
}
