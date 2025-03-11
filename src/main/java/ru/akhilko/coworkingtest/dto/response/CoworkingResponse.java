package ru.akhilko.coworkingtest.dto.response;

import java.time.Instant;
import java.util.List;

public record CoworkingResponse(String id, String address, List<CoworkingResponse.Room> rooms) {
    public record Room(String id, String label, List<CoworkingResponse.Place> places,
                       List<CoworkingResponse.Reservation> reservations) {}
    public record Place(String id, String label) {}
    public record Reservation(String id, Instant reservedFrom, Instant reservedTo) {}
}
