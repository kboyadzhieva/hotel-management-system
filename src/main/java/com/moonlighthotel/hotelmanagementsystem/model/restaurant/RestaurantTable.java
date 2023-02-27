package com.moonlighthotel.hotelmanagementsystem.model.restaurant;

import com.moonlighthotel.hotelmanagementsystem.enumeration.SectionType;
import com.moonlighthotel.hotelmanagementsystem.enumeration.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ZoneType zone;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer people;

    @Column(nullable = false)
    private SectionType smoking;
}
