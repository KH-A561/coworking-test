package ru.akhilko.coworkingtest.dto;

import java.util.List;

public record CoworkingDto(String address, List<RoomDto> rooms) {
}
