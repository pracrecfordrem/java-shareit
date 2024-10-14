package ru.practicum.shareit.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.InternalServerException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ObjectMapper objectMapper;
    private final ItemRepository itemRepository;

    public ItemRequest addRequest(Long userId, String itemDescription) {
        Map<String,String> map;
        try {
            map = objectMapper.readValue(itemDescription, Map.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("Ошибка парсинга Json" + e.getMessage());
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRequestRepository.save(ItemRequestMapper.toItemRequest(map.get("description"), user));
    }


    public List<ItemRequestDto> getRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(itemRequest -> {
                    List<Item> itemList = itemRepository.findAllByRequestId(itemRequest.getId());
                    return ItemRequestMapper.toItemRequestDto(itemRequest,itemList);
                })
                .toList();
    }

    @Override
    public List<ItemRequestDto> getRequestsAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRequestRepository.findALLWhereItemNotFound(userId)
                .stream()
                .sorted()
                .map(itemRequest -> ItemRequestMapper.toItemRequestDto(itemRequest,null))
                .toList();
    }

    @Override
    public ItemRequestDto getRequestsById(Long userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return itemRequestRepository
                .findById(requestId)
                .map(itemRequest -> {
                    List<Item> itemList = itemRepository.findAllByRequestId(itemRequest.getId());
                    return ItemRequestMapper.toItemRequestDto(itemRequest,itemList);
                    })
                .orElse(null);
    }
}
