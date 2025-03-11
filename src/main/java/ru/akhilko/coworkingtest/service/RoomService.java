package ru.akhilko.coworkingtest.service;

import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.dto.response.RoomResponse;

import java.time.Instant;
import java.util.List;

public interface RoomService {
    RoomResponse getById(Long roomId);

    RoomResponse create(CreateRoomRequest request);

    RoomResponse update(Long roomId, UpdateRoomRequest request);

    void deleteById(Long roomId);

    List<RoomResponse> getFreeWithFiltering(Instant freeFrom, Instant freeTo, Integer atLeastPlaces);
}
