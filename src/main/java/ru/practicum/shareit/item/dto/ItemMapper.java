package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;


public class ItemMapper {
    public static ItemDto toItemDtoExtended(Item item, BookingDto lastBookingDto, BookingDto nextBookingDto, List<CommentDto> commentList) {
        Long requestId = item.getRequest() == null ? null : item.getRequest().getId();
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                requestId,
                item.getUser().getId(),
                lastBookingDto,
                nextBookingDto,
                commentList
        );
    }

    public static ItemDto toItemDto(Item item) {
        Long requestId = item.getRequest() == null ? null : item.getRequest().getId();
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                requestId,
                item.getUser().getId()
        );
    }

    public static Item toItem(ItemDto item, ItemRequest request, User user) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                user,
                request
        );
    }

    public static Comment toComment(CommentDto commentDto, Item item, User author) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                item,
                author,
                LocalDateTime.now()
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        Long itemId = comment.getItem() == null ? null : comment.getItem().getId();
        String authorName = comment.getAuthorName() == null ? null : comment.getAuthorName().getName();
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                itemId,
                authorName,
                comment.getCreated()
        );
    }
}
