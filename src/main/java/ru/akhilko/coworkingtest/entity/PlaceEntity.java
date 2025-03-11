package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "place")
@ToString(exclude = "room")
public class PlaceEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id_seq")
    @Id
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "room_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RoomEntity room;

    @Column(nullable = false)
    private String label;
}
