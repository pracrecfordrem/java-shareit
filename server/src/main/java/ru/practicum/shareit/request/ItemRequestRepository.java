package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest,Long> {

    List<ItemRequest> findAllByRequesterId(Long ownerId);

    @Query(nativeQuery = true,
          value = "select r.*" +
                  "  from requests r" +
                  "  left join items i" +
                  "    on r.id = i.request_id" +
                  " where i.id is null" +
                  "   and r.requester_id != ?1")
    List<ItemRequest> findALLWhereItemNotFound(Long requesterId);
}
