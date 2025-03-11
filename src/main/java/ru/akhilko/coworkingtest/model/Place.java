package ru.akhilko.coworkingtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Place(Long id, @JsonIgnore Room room, String label) {
}
