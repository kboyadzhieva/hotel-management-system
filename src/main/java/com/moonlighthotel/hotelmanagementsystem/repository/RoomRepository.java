package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByTitle(String title);

    @Query(value = "SELECT r FROM Room r " +
            "WHERE(r.people >= ?3 AND r.id NOT IN(SELECT rr.room FROM RoomReservation rr)) " +
            "OR(r.people >= ?3 AND r.id IN(SELECT rr.room FROM RoomReservation rr " +
            "WHERE (rr.startDate NOT BETWEEN ?1 AND ?2) AND (rr.endDate NOT BETWEEN ?1 AND ?2))) " +
            "OR(r.people >= ?3 AND r.id IN(SELECT COUNT(rr.room) FROM RoomReservation rr " +
            "WHERE (rr.startDate BETWEEN ?1 AND ?2) AND (rr.endDate BETWEEN ?1 AND ?2) " +
            "GROUP BY rr.room " +
            "HAVING COUNT(rr.room) < r.count " +
            "ORDER BY r.id))")
    List<Room> findAllAvailableRooms(Instant startDate, Instant endDate, Integer adults, Integer kids);
}
