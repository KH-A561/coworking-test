package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public record UpdateRoomRequest(@NotBlank String coworkingId, @NotBlank String label,
                                @NotNull List<Place> places) {
    public record Place(Long id, @NotBlank String label) {}
}
