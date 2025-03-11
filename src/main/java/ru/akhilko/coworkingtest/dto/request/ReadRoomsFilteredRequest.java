package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Optional;

public record ReadRoomsFilteredRequest(@NotNull TimePeriod timePeriod, Optional<Integer> atLeastPlaces) {
    public record TimePeriod(@NotNull Instant freeFrom, @NotNull Instant freeTo) {}
}
