package ru.akhilko.coworkingtest.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.dto.response.RoomResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.entity.PlaceEntity;
import ru.akhilko.coworkingtest.entity.RoomEntity;
import ru.akhilko.coworkingtest.mapper.RoomMapper;
import ru.akhilko.coworkingtest.model.Room;
import ru.akhilko.coworkingtest.repo.CoworkingRepository;
import ru.akhilko.coworkingtest.repo.RoomRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RoomServiceImplTest {
    private static final UUID COWORKING_ID = UUID.randomUUID();
    private static final Long ROOM_ID = 1L;
    private static final String ROOM = "ROOM";

    @Mock
    private CoworkingRepository coworkingRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl uut;

    @Test
    void getById() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(ROOM_ID);
        roomEntity.setLabel(ROOM);
        Room room = new Room(ROOM_ID, null, ROOM, Collections.emptyList(), Collections.emptyList());
        RoomResponse response = new RoomResponse(
                room.id().toString(), null, room.label(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(roomEntity));
        Mockito.when(roomMapper.daoToModel(roomEntity)).thenReturn(room);
        Mockito.when(roomMapper.modelToDto(room)).thenReturn(response);

        var actual = uut.getById(ROOM_ID);

        Assertions.assertEquals(response, actual);
    }

    @Test
    void create() {
        CreateRoomRequest request = new CreateRoomRequest(COWORKING_ID.toString(), ROOM, null);
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        Room room = new Room(ROOM_ID, null, ROOM, null, null);
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCoworking(coworkingEntity);
        roomEntity.setLabel(ROOM);
        RoomResponse response = new RoomResponse(
                room.id().toString(), null, room.label(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(coworkingRepository.existsById(COWORKING_ID)).thenReturn(true);
        Mockito.when(coworkingRepository.getReferenceById(COWORKING_ID)).thenReturn(coworkingEntity);
        Mockito.when(roomMapper.dtoToModel(request)).thenReturn(room);
        Mockito.when(roomMapper.modelToDao(room)).thenReturn(roomEntity);
        Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
        Mockito.when(roomMapper.daoToModel(roomEntity)).thenReturn(room);
        Mockito.when(roomMapper.modelToDto(room)).thenReturn(response);

        var actual = uut.create(request);

        Assertions.assertEquals(response, actual);
    }

    @Test
    void getFreeWithFiltering() {
        Instant freeFrom = Instant.now();
        Instant freeTo = freeFrom.plus(1, ChronoUnit.HOURS);
        Integer atLeastPlaces = 2;

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(ROOM_ID);
        roomEntity.setLabel(ROOM);
        roomEntity.setPlaces(List.of(new PlaceEntity(), new PlaceEntity()));
        Room room = new Room(ROOM_ID, null, ROOM, Collections.emptyList(), Collections.emptyList());
        List<RoomResponse> response = List.of(new RoomResponse(
                room.id().toString(), null, room.label(), Collections.emptyList(), Collections.emptyList()));

        Mockito.when(roomRepository.findRoomsFreeWithinTime(freeFrom, freeTo)).thenReturn(List.of(roomEntity));
        Mockito.when(roomMapper.daoToModel(List.of(roomEntity))).thenReturn(List.of(room));
        Mockito.when(roomMapper.modelToDto(List.of(room))).thenReturn(response);

        var actual = uut.getFreeWithFiltering(freeFrom, freeTo, atLeastPlaces);

        Assertions.assertEquals(response, actual);
    }

    @Test
    void update() {
        UpdateRoomRequest request = new UpdateRoomRequest(COWORKING_ID.toString(), ROOM, null);
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        Room room = new Room(ROOM_ID, null, ROOM, null, null);
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCoworking(coworkingEntity);
        roomEntity.setLabel(ROOM);
        roomEntity.setPlaces(Collections.emptyList());
        RoomResponse response = new RoomResponse(
                room.id().toString(), null, room.label(), Collections.emptyList(), Collections.emptyList());

        Mockito.when(coworkingRepository.existsById(COWORKING_ID)).thenReturn(true);
        Mockito.when(coworkingRepository.getReferenceById(COWORKING_ID)).thenReturn(coworkingEntity);
        Mockito.when(roomMapper.modelToDao(room)).thenReturn(roomEntity);
        Mockito.when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.of(roomEntity));
        Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
        Mockito.when(roomMapper.daoToModel(roomEntity)).thenReturn(room);
        Mockito.when(roomMapper.modelToDto(room)).thenReturn(response);

        var actual = uut.update(ROOM_ID, request);

        Assertions.assertEquals(response, actual);
    }

    @Test
    void deleteById() {
        uut.deleteById(ROOM_ID);

        Mockito.verify(roomRepository).deleteById(ROOM_ID);
    }
}