package com.moonlighthotel.hotelmanagementsystem.model;

import com.moonlighthotel.hotelmanagementsystem.enumeration.BedType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.StatusType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.ViewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false)
    private Integer adults;

    @Column(nullable = false)
    private Integer kids;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BedType typeBed;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ViewType view;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Instant created;

    @Column(nullable = false)
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
