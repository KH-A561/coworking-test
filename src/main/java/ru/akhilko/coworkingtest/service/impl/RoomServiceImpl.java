package ru.akhilko.coworkingtest.service.impl;

import org.springframework.stereotype.Service;
import ru.akhilko.coworkingtest.dto.RoomDto;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.service.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Override
    public RoomDto getById(String roomId) {
        return null;
    }

    @Override
    public RoomDto create(CreateRoomRequest request) {
        return null;
    }

    @Override
    public List<RoomDto> getAll() {
        return List.of();
    }

    @Override
    public RoomDto update(String roomId, UpdateRoomRequest request) {
        return null;
    }

    @Override
    public void deleteById(String roomId) {

    }
}
