package ru.practicum.shareit.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {

    ItemRequest addRequest(Long userId, String itemDescription);

    List<ItemRequestDto> getRequests(Long userId);

    List<ItemRequestDto> getRequestsAll(Long userId);

    ItemRequestDto getRequestsById(Long userId, Long requestId);
}
