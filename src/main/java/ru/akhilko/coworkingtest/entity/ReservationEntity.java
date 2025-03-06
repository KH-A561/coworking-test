package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public record ReservationEntity(
        @GeneratedValue(strategy = GenerationType.UUID)
        @Id @Column UUID id,
        @Column(nullable = false) Instant reservedFrom,
        @Column Instant reservedTo,
        @JoinColumn(name = "place_id") @OneToOne(optional = false) PlaceEntity place) {
}
