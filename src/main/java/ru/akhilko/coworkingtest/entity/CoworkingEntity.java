package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "coworking")
public record CoworkingEntity(
        @GeneratedValue(strategy = GenerationType.UUID)
        @Id @Column UUID id,
        @Column(nullable = false) String address,
        @Column @OneToMany(cascade = CascadeType.ALL, mappedBy = "coworking") List<RoomEntity> rooms) {
}
