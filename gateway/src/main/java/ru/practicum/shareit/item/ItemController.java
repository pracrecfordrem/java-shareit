package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@Positive @RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody @Valid ItemRequestDto requestDto) {
        return itemClient.createItem(userId,requestDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> postComment(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                              @Positive @PathVariable("itemId") Long itemId,
                                              @Valid @RequestBody CommentRequestDto requestDto) {
        return itemClient.postComment(userId, itemId, requestDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> postComment(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                              @Positive @PathVariable("itemId") Long itemId) {
        return itemClient.getItem(userId, itemId);
    }

}
