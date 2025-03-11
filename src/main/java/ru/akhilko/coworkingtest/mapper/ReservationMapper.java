package ru.akhilko.coworkingtest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akhilko.coworkingtest.dto.response.ReservationResponse;
import ru.akhilko.coworkingtest.entity.ReservationEntity;
import ru.akhilko.coworkingtest.model.Reservation;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Named("ReservationMapper")
@Mapper(componentModel = SPRING)
public interface ReservationMapper {
    @Named("daoToModel")
    @Mapping(target = "room", ignore = true)
    Reservation daoToModel(ReservationEntity reservationEntity);

    ReservationResponse modelToResponse(Reservation reservation);
}
