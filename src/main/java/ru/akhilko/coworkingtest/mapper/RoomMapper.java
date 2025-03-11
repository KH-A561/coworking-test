package ru.akhilko.coworkingtest.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.response.RoomResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.entity.PlaceEntity;
import ru.akhilko.coworkingtest.entity.ReservationEntity;
import ru.akhilko.coworkingtest.entity.RoomEntity;
import ru.akhilko.coworkingtest.model.Coworking;
import ru.akhilko.coworkingtest.model.Place;
import ru.akhilko.coworkingtest.model.Reservation;
import ru.akhilko.coworkingtest.model.Room;

import java.util.Collections;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Named("RoomMapper")
@Mapper(componentModel = SPRING)
public interface RoomMapper {
    @Named("daoToModel")
    @Mapping(target = "coworking", expression = "java(fillWithCoworking(roomEntity))")
    @Mapping(target = "places", expression = "java(fillWithPlaces(roomEntity))")
    @Mapping(target = "reservations", expression = "java(fillWithReservations(roomEntity))")
    Room daoToModel(RoomEntity roomEntity);

    @Named("daoToModelWithoutCoworking")
    @Mapping(target = "coworking", ignore = true)
    @Mapping(target = "places", expression = "java(fillWithPlaces(roomEntity))")
    @Mapping(target = "reservations", expression = "java(fillWithReservations(roomEntity))")
    Room daoToModelWithoutCoworking(RoomEntity roomEntity);

    @Mapping(target = "coworkingId", source = "coworking.id")
    RoomResponse modelToDto(Room room);

    Room dtoToModel(CreateRoomRequest request);

    @Named("modelToDao")
    RoomEntity modelToDao(Room room);

    @IterableMapping(qualifiedByName = "daoToModel")
    List<Room> daoToModel(List<RoomEntity> roomEntities);

    List<RoomResponse> modelToDto(List<Room> rooms);

    @Named("fillWithCoworking")
    default Coworking fillWithCoworking(RoomEntity dao) {
        CoworkingEntity entity = dao.getCoworking();
        return new Coworking(entity.getId(), entity.getAddress(), null);
    }

    @Named("fillWithPlaces")
    default List<Place> fillWithPlaces(RoomEntity dao) {
        List<PlaceEntity> entities = dao.getPlaces();
        return entities == null ? Collections.emptyList()
                : entities.stream().map(e -> new Place(e.getId(), null, e.getLabel())).toList();
    }

    @Named("fillWithReservations")
    default List<Reservation> fillWithReservations(RoomEntity dao) {
        List<ReservationEntity> entities = dao.getReservations();
        return entities == null
                ? Collections.emptyList()
                : entities.stream()
                .map(e -> new Reservation(e.getId(), e.getReservedTo(), e.getReservedFrom(), null))
                .toList();
    }
}
