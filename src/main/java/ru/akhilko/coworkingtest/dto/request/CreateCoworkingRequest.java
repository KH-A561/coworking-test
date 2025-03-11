package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCoworkingRequest(@NotBlank String address, @NotNull List<CreateCoworkingRequest.Room> rooms) {
    public record Room(@NotBlank String label,
                       @NotNull List<CreateCoworkingRequest.Place> places) {}
    public record Place(@NotBlank String label) {}
}
