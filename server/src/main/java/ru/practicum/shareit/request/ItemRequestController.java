package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequest addRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @RequestBody String itemDescription) {
        return itemRequestService.addRequest(userId, itemDescription);
    }

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestsAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getRequestsAll(userId);
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @PathVariable Long requestId) {
        return itemRequestService.getRequestsById(userId, requestId);
    }
}
