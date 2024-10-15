package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query(nativeQuery = true, value =
           "select b.*" +
           "  from bookings b" +
           " inner join items i" +
           "    on i.id = b.item_id " +
           " inner join users u" +
           "    on u.id = i.owner_id "         +
           " where b.end_date < now()" +
           "   and b.item_id = ?1 " +
           "   and u.id = ?2 " +
           " order by end_date desc" +
           " limit 1")
    Booking findLastBooking(Long itemId, Long userId);

    @Query(nativeQuery = true, value =
            "select b.*" +
            "  from bookings b" +
            " inner join items i" +
            "    on i.id = b.item_id " +
            " inner join users u" +
            "    on u.id = i.owner_id "         +
            " where b.start_date > now()" +
            "   and b.item_id = ?1 " +
            "   and u.id = ?2 " +
            " order by end_date desc" +
            " limit 1")
    Booking findNextBooking(Long itemId, Long userId);


    Optional<Booking> findBookingByBookerIdAndItemId(Long bookerId, Long itemId);

    @Query("select u " +
            " from ru.practicum.shareit.booking.model.Booking b" +
            " join b.item i" +
            " join i.user u " +
            "where b.id = ?1")
    User findUserByBooking(Long bookingId);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            " where b.booker_id = ?1 " +
            "   and b.status like '%' || ?2 || '%'" +
            "   and b.start_date < ?3" +
            "   and b.end_date > ?4")
    List<Booking> findAllByBookerAndState(Long userId, String status, LocalDateTime start, LocalDateTime end);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            " where b.booker_id = ?1 " +
            "   and b.start_date > now()")
    List<Booking> findAllByBookerAndStateFuture(Long userId);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            " where b.booker_id = ?1 " +
            "   and b.end_date < now()")
    List<Booking> findAllByBookerAndStatePast(Long userId);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            "  join items i" +
            "    on i.id = b.item_id" +
            " where i.owner_id = ?1 " +
            "   and b.status like '%' || ?2 || '%'" +
            "   and b.start_date < ?3" +
            "   and b.end_date > ?4")
    List<Booking> findAllByOwnerAndState(Long userId, String rejected, LocalDateTime max, LocalDateTime min);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            "  join items i" +
            "    on i.id = b.item_id" +
            " where i.owner_id = ?1 " +
            "   and b.start_date > now()")
    List<Booking> findAllByOwnerAndStateFuture(Long userId);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            "  join items i" +
            "    on i.id = b.item_id" +
            " where i.owner_id = ?1 " +
            "   and b.end_date < now()")
    List<Booking> findAllByOwnerAndStatePast(Long userId);

    @Query(nativeQuery = true, value =
            "select b.* " +
            "  from bookings b" +
            " where b.item_id = ?1 " +
            "   and b.start_date <= ?2" +
            "   and b.end_date >= ?2" +
            "   and b.status != 'REJECTED'")
    List<Booking> findAllItemBookings(Long itemId, LocalDateTime start);
}
