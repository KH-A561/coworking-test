package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateReservationRequest(@NotNull Long roomId, @NotNull Instant from, @NotNull Instant to) {
}
