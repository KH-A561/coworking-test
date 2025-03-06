package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room")
public record RoomEntity(
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_seq")
        @Id @Column(nullable = false) Long id,
        @JoinColumn(name = "coworking_id") @ManyToOne(optional = false) CoworkingEntity coworking,
        @Column @OneToMany(cascade = CascadeType.ALL, mappedBy = "room") List<PlaceEntity> places) {
}
