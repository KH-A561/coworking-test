package ru.akhilko.coworkingtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.UUID;

public record Reservation(UUID id, Instant reservedFrom, Instant reservedTo, @JsonIgnore Room room) {
}
