package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRoomRequest(@NotBlank String coworkingId, @NotBlank String label,
                                @NotNull List<CreateRoomRequest.Place> places) {
    public record Place(@NotBlank String label) {}
}
