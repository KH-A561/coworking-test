package ru.akhilko.coworkingtest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "reservation")
@ToString(exclude = "room")
public class ReservationEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private Instant reservedFrom;

    @Column(nullable = false)
    private Instant reservedTo;

    @JoinColumn(name = "room_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RoomEntity room;
}
