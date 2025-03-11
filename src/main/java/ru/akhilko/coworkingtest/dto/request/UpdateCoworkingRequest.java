package ru.akhilko.coworkingtest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCoworkingRequest(@NotBlank String address) {
}
