package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Entity(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    @JoinColumn(name = "owner_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @JoinColumn(name = "request_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemRequest request;

    public Item(String name, String description, Boolean available, User user, ItemRequest request) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.user = user;
        this.request = request;
    }
}

