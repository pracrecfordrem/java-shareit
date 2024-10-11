package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
@Entity(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @JoinColumn(name = "requestor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User requestor;

}
