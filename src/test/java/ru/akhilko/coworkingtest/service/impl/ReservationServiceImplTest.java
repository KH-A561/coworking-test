package ru.akhilko.coworkingtest.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.akhilko.coworkingtest.dto.request.CreateReservationRequest;
import ru.akhilko.coworkingtest.dto.response.ReservationResponse;
import ru.akhilko.coworkingtest.entity.ReservationEntity;
import ru.akhilko.coworkingtest.entity.RoomEntity;
import ru.akhilko.coworkingtest.mapper.CoworkingMapper;
import ru.akhilko.coworkingtest.mapper.ReservationMapper;
import ru.akhilko.coworkingtest.model.Reservation;
import ru.akhilko.coworkingtest.model.Room;
import ru.akhilko.coworkingtest.repo.CoworkingRepository;
import ru.akhilko.coworkingtest.repo.ReservationRepository;
import ru.akhilko.coworkingtest.repo.RoomRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReservationServiceImplTest {
    private final static Long ROOM_ID = 1L;

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl uut;

    @Test
    void create() {
        CreateReservationRequest request = new CreateReservationRequest(
                ROOM_ID, Instant.now(), Instant.now().plus(1, ChronoUnit.HOURS));

        RoomEntity room = new RoomEntity();
        room.setId(ROOM_ID);

        List<RoomEntity> freeRooms = List.of(room);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setReservedFrom(request.from());
        reservation.setReservedTo(request.to());
        reservation.setRoom(room);

        Reservation reservationModel = new Reservation(
                UUID.randomUUID(), reservation.getReservedFrom(), reservation.getReservedTo(), null);

        ReservationResponse reservationResponse = new ReservationResponse(
                reservationModel.id(), reservationModel.reservedFrom(), reservationModel.reservedTo());

        Mockito.when(roomRepository.findRoomsFreeWithinTime(request.from(), request.to())).thenReturn(freeRooms);
        Mockito.when(roomRepository.getReferenceById(ROOM_ID)).thenReturn(room);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(reservationMapper.daoToModel(reservation)).thenReturn(reservationModel);
        Mockito.when(reservationMapper.modelToResponse(reservationModel)).thenReturn(reservationResponse);

        ReservationResponse actual = uut.create(request);

        Mockito.verify(reservationRepository).save(reservation);
        Mockito.verify(reservationMapper).daoToModel(reservation);
        Mockito.verify(reservationMapper).modelToResponse(reservationModel);

        Assertions.assertEquals(reservationResponse, actual);
    }
}