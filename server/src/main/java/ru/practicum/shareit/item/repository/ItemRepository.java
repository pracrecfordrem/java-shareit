package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query("select i from ru.practicum.shareit.item.model.Item as i " +
            "join i.user as u " +
            "where u.id = ?1")
    List<Item> getAllByOwnerId(Long ownerId);

    List<Item> findByNameContainingIgnoreCase(String text);

    List<Item> findByNameNull();

    List<Item> findAllByRequestId(Long requestId);

}
