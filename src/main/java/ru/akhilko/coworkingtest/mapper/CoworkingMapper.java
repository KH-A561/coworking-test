package ru.akhilko.coworkingtest.mapper;

import org.mapstruct.*;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.response.CoworkingResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.model.Coworking;
import ru.akhilko.coworkingtest.model.Place;
import ru.akhilko.coworkingtest.model.Room;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Named("CoworkingMapper")
@Mapper(componentModel = SPRING, uses = {RoomMapper.class})
public interface CoworkingMapper {
    @Named("daoToModel")
    @Mapping(target = "rooms", ignore = true)
    Coworking daoToModel(CoworkingEntity dao);

    @Named("daoToModelWithRooms")
    @Mapping(target = "rooms", qualifiedByName = "daoToModelWithoutCoworking")
    Coworking daoToModelWithRooms(CoworkingEntity dao);

    Coworking dtoToModel(CreateCoworkingRequest dto);

    Coworking dtoToModel(UpdateCoworkingRequest dto);

    CoworkingResponse modelToDto(Coworking model);

    CoworkingEntity modelToDao(Coworking model);

    @IterableMapping(qualifiedByName = "daoToModelWithRooms")
    List<Coworking> daoToModelWithRooms(List<CoworkingEntity> dao);

    List<CoworkingResponse> modelToDto(List<Coworking> model);

    Place map(CreateCoworkingRequest.Place place);

    Room map(CreateCoworkingRequest.Room room);
}
