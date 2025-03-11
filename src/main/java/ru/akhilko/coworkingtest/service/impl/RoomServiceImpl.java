package ru.akhilko.coworkingtest.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.dto.response.RoomResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.entity.PlaceEntity;
import ru.akhilko.coworkingtest.entity.RoomEntity;
import ru.akhilko.coworkingtest.exception.ExceptionMessage;
import ru.akhilko.coworkingtest.mapper.RoomMapper;
import ru.akhilko.coworkingtest.model.Room;
import ru.akhilko.coworkingtest.repo.CoworkingRepository;
import ru.akhilko.coworkingtest.repo.RoomRepository;
import ru.akhilko.coworkingtest.service.RoomService;

import java.time.Instant;
import java.util.*;

import static ru.akhilko.coworkingtest.exception.ExceptionMessage.COWORKING_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final CoworkingRepository coworkingRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomResponse getById(Long roomId) {
        return entityToResponseWithException(roomRepository.findById(roomId));
    }

    @Override
    @Transactional
    public RoomResponse create(CreateRoomRequest request) {
        UUID coworkingId = UUID.fromString(request.coworkingId());
        if (!coworkingRepository.existsById(coworkingId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), COWORKING_NOT_FOUND.getMessage());
        }

        CoworkingEntity coworking = coworkingRepository.getReferenceById(coworkingId);
        Room room = roomMapper.dtoToModel(request);
        RoomEntity roomEntity = roomMapper.modelToDao(room);
        roomEntity.setCoworking(coworking);
        Optional.ofNullable(roomEntity.getPlaces()).orElse(Collections.emptyList())
                .forEach(p -> p.setRoom(roomEntity));

        return entityToResponseWithException(Optional.of(roomRepository.save(roomEntity)));
    }

    @Override
    public List<RoomResponse> getFreeWithFiltering(Instant freeFrom, Instant freeTo, Integer atLeastPlaces) {
        if (freeFrom.isAfter(freeTo)) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400), ExceptionMessage.INVALID_TIME_PERIOD.getMessage());
        }

        List<RoomEntity> freeRooms = roomRepository.findRoomsFreeWithinTime(freeFrom, freeTo);

        return roomMapper.modelToDto(roomMapper.daoToModel(
                        atLeastPlaces > 0
                                ? freeRooms.stream().filter(room -> room.getPlaces().size() >= atLeastPlaces).toList()
                                : freeRooms
                )
        );
    }

    @Override
    @Transactional
    public RoomResponse update(Long roomId, UpdateRoomRequest request) {
        RoomEntity roomEntityFromDb = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        UUID coworkingId = UUID.fromString(request.coworkingId());
        if (!coworkingRepository.existsById(coworkingId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), COWORKING_NOT_FOUND.getMessage());
        }

        roomEntityFromDb.setCoworking(coworkingRepository.getReferenceById(coworkingId));
        roomEntityFromDb.setLabel(request.label());

        List<PlaceEntity> newPlaces = new ArrayList<>();
        if (request.places() != null) {
            request.places().forEach(p -> {
                PlaceEntity placeEntity = new PlaceEntity();
                placeEntity.setRoom(roomEntityFromDb);
                placeEntity.setLabel(p.label());
                placeEntity.setId(p.id());
                newPlaces.add(placeEntity);
            });
        }

        roomEntityFromDb.updatePlaces(newPlaces);

        return entityToResponseWithException(Optional.of(roomRepository.save(roomEntityFromDb)));
    }

    @Override
    public void deleteById(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    private RoomResponse entityToResponseWithException(Optional<RoomEntity> roomEntity) {
        return roomEntity.map(roomMapper::daoToModel)
                .map(roomMapper::modelToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                        ExceptionMessage.ROOM_ENTITY_HAS_NOT_BEEN_FOUND.getMessage()));
    }
}
