package com.moonlighthotel.hotelmanagementsystem.repository;

import com.moonlighthotel.hotelmanagementsystem.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    List<RoomReservation> findAllByRoomId(Long id);

    @Query(value = "SELECT COUNT(rr) FROM RoomReservation rr " +
            "WHERE(rr.room.id = ?1 and rr.startDate BETWEEN ?2 and ?3) " +
            "OR(rr.room.id = ?1 and rr.endDate BETWEEN ?2 and ?3) " +
            "OR(rr.room.id = ?1 and ?2 BETWEEN rr.startDate and rr.endDate) " +
            "OR(rr.room.id = ?1 and ?3 BETWEEN rr.startDate and rr.endDate) " +
            "GROUP BY rr.room.id")
    Integer countTheReservationsForASpecificRoom(Long id, Instant startDate, Instant endDate);
}
