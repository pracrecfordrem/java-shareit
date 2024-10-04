package ru.practicum.shareit.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Entity(name = "bookings")
@AllArgsConstructor
public class Booking implements Comparable<Booking>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;
    private String status = "WAITING";

    public Booking() {
    }

    @Override
    public int compareTo(Booking o) {
        if (o.getStart().isBefore(this.getStart())) {
            return 1;
        } else if (o.getStart().isAfter(this.getStart())) {
            return -1;
        } else {
            return 0;
        }
    }
}
