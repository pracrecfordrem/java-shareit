package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Entity(name = "requests")
public class ItemRequest implements Comparable<ItemRequest>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private String description;
    @JoinColumn(name = "requester_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;

    public ItemRequest(LocalDateTime created, String description, User requester) {
        this.created = created;
        this.description = description;
        this.requester = requester;
    }

    public ItemRequest() {
    }

    @Override
    public int compareTo(ItemRequest o) {
        if (o.created.isAfter(this.created)) {
            return 1;
        } else if (o.created.isBefore(this.created)) {
            return -1;
        } else {
            return 0;
        }
    }
}
