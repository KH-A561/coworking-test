package ru.akhilko.coworkingtest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.akhilko.coworkingtest.entity.PlaceEntity;
import ru.akhilko.coworkingtest.model.Place;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Named("PlaceMapper")
@Mapper(componentModel = SPRING)
public interface PlaceMapper {
    @Named("daoToModel")
    @Mapping(target = "room", ignore = true)
    Place daoToModel(PlaceEntity placeEntity);
}
