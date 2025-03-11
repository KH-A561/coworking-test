package ru.akhilko.coworkingtest.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(UUID id, Instant reservedFrom, Instant reservedTo) {
}
