package ru.akhilko.coworkingtest.service;

import ru.akhilko.coworkingtest.dto.RoomDto;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;

import java.util.List;

public interface RoomService {
    RoomDto getById(String roomId);

    RoomDto create(CreateRoomRequest request);

    List<RoomDto> getAll();

    RoomDto update(String roomId, UpdateRoomRequest request);

    void deleteById(String roomId);
}
