package ru.akhilko.coworkingtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record Room(Long id,
                   @JsonIgnore
                   Coworking coworking,
                   String label,
                   List<Place> places,
                   List<Reservation> reservations) {
}
