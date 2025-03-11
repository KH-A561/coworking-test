package ru.akhilko.coworkingtest.dto.response;

import java.time.Instant;
import java.util.List;

public record RoomResponse(String id, String coworkingId,
                           String label, List<RoomResponse.Place> places, List<RoomResponse.Reservation> reservations) {
    public record Place(String id, String label) {}
    public record Reservation(String id, Instant reservedFrom, Instant reservedTo) {}
}
