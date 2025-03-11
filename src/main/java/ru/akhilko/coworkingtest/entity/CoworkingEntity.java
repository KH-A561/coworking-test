package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "coworking")
public class CoworkingEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coworking")
    private List<RoomEntity> rooms;
}
