package com.moonlighthotel.hotelmanagementsystem.model.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CarCategory carCategory;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private String image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "car_id")
    private Set<CarImage> images;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Instant created;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<CarTransfer> carTransfers;
}
