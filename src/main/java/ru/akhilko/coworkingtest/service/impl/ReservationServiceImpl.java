package ru.akhilko.coworkingtest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.akhilko.coworkingtest.dto.request.CreateReservationRequest;
import ru.akhilko.coworkingtest.dto.response.ReservationResponse;
import ru.akhilko.coworkingtest.entity.ReservationEntity;
import ru.akhilko.coworkingtest.entity.RoomEntity;
import ru.akhilko.coworkingtest.exception.ExceptionMessage;
import ru.akhilko.coworkingtest.mapper.ReservationMapper;
import ru.akhilko.coworkingtest.repo.ReservationRepository;
import ru.akhilko.coworkingtest.repo.RoomRepository;
import ru.akhilko.coworkingtest.service.ReservationService;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatusCode.valueOf;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse create(CreateReservationRequest request) {
        Instant reserveFrom = request.from();
        Instant reserveTo = request.to();

        if (reserveFrom.isAfter(reserveTo)) {
            throw new ResponseStatusException(
                    valueOf(400), ExceptionMessage.INVALID_TIME_PERIOD.getMessage());
        }

        List<RoomEntity> freeRooms = roomRepository.findRoomsFreeWithinTime(reserveFrom, reserveTo);
        RoomEntity freeRoom = freeRooms.stream().filter(room -> room.getId().equals(request.roomId()))
                .findFirst().orElseThrow(
                        () -> new ResponseStatusException(valueOf(400),
                                ExceptionMessage.ROOM_ENTITY_HAS_NOT_BEEN_FOUND.getMessage())
                );

        ReservationEntity reservation = new ReservationEntity();
        reservation.setReservedFrom(reserveFrom);
        reservation.setReservedTo(reserveTo);
        reservation.setRoom(roomRepository.getReferenceById(freeRoom.getId()));

        return reservationMapper.modelToResponse(
                reservationMapper.daoToModel(reservationRepository.save(reservation))
        );
    }
}
