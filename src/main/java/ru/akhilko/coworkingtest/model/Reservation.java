package ru.akhilko.coworkingtest.model;

import java.time.Instant;

public record Reservation(Instant reservedFrom, Instant reservedTo, Place place) {
}
