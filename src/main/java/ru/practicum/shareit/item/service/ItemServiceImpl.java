package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
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
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Item addItem(ItemDto itemDto, Long userId) {
        ItemRequest request;
        if (itemDto.getRequestId() == null) {
            request = null;
        } else {
            request = itemRequestRepository.findById(itemDto.getRequestId()).orElseThrow(() -> new NotFoundException("Request not found"));
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
    public ItemDto getItem(Long userId, Long itemId) {
        Booking lastBooking = bookingRepository.findLastBooking(itemId, userId);
        Booking nextBooking = bookingRepository.findNextBooking(itemId, userId);
        BookingDto lastBookingDto = lastBooking == null
                ? null : BookingMapper.toBookingDto(lastBooking);
        BookingDto nextBookingDto = nextBooking == null
                ? null : BookingMapper.toBookingDto(nextBooking);
        List<Comment> comments = commentRepository.findCommentsByItemId(itemId);
        List<CommentDto> commentDtoList  = comments.stream().map(ItemMapper::toCommentDto).toList();
        return ItemMapper.toItemDtoExtended(itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found")),
                lastBookingDto,nextBookingDto,commentDtoList);
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

    @Override
    public CommentDto postComment(Long userId, Long itemId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Booking booking = bookingRepository.findBookingByBookerIdAndItemId(userId,itemId).orElseThrow(() ->
                new ValidationException("Booking didn't happen"));
        Long commentId = commentRepository.save(ItemMapper.toComment(commentDto,item,user)).getId();
        CommentDto resultCommentDto =  ItemMapper.toCommentDto(commentRepository.findById(commentId).get());
        if (resultCommentDto.getCreated().isBefore(booking.getEnd())) {
            throw new ValidationException("Trying to comment before using an item");
        }
        return resultCommentDto;
    }
}
