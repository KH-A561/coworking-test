package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "place")
public record PlaceEntity(
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id_seq")
        @Id @Column(nullable = false) Long id,
        @JoinColumn(name = "room_id", nullable = false) @ManyToOne RoomEntity room,
        @Column(nullable = false) String status) {
}
