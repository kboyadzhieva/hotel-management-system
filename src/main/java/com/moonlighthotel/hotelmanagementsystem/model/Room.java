package com.moonlighthotel.hotelmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Set<String> images;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String facilities;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Integer people;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer count;
}
