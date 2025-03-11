package ru.akhilko.coworkingtest.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.response.CoworkingResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.mapper.CoworkingMapper;
import ru.akhilko.coworkingtest.model.Coworking;
import ru.akhilko.coworkingtest.repo.CoworkingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CoworkingServiceTest {
    private static final UUID UUID = java.util.UUID.randomUUID();
    private static final String ADDRESS = "ADDRESS";

    @Mock
    private CoworkingRepository coworkingRepository;
    @Mock
    private CoworkingMapper coworkingMapper;

    @InjectMocks
    private CoworkingServiceImpl uut;

    @Test
    void whenGetById_andEntityFound_thenSuccess() {
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        Coworking coworking = new Coworking(UUID, ADDRESS, new ArrayList<>());
        CoworkingResponse coworkingResponse = new CoworkingResponse(
                coworking.id().toString(), coworking.address(), new ArrayList<>());

        Mockito.when(coworkingRepository.findById(UUID)).thenReturn(Optional.of(coworkingEntity));
        Mockito.when(coworkingMapper.daoToModelWithRooms(coworkingEntity)).thenReturn(coworking);
        Mockito.when(coworkingMapper.modelToDto(coworking)).thenReturn(coworkingResponse);

        assertEquals(coworkingResponse, uut.getById(UUID.toString()));
    }

    @Test
    void whenGetById_andEntityNotFound_thenThrow() {
        assertThrows(ResponseStatusException.class, () -> uut.getById(UUID.toString()));
    }

    @Test
    void create() {
        CreateCoworkingRequest request = new CreateCoworkingRequest(ADDRESS, new ArrayList<>());
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        Coworking coworking = new Coworking(UUID, ADDRESS, new ArrayList<>());
        CoworkingResponse coworkingResponse = new CoworkingResponse(
                coworking.id().toString(), coworking.address(), new ArrayList<>());

        Mockito.when(coworkingMapper.dtoToModel(request)).thenReturn(coworking);
        Mockito.when(coworkingMapper.modelToDao(coworking)).thenReturn(coworkingEntity);
        Mockito.when(coworkingRepository.save(coworkingEntity)).thenReturn(coworkingEntity);
        Mockito.when(coworkingMapper.daoToModelWithRooms(coworkingEntity)).thenReturn(coworking);
        Mockito.when(coworkingMapper.modelToDto(coworking)).thenReturn(coworkingResponse);

        assertEquals(coworkingResponse, uut.create(request));
    }

    @Test
    void update() {
        UpdateCoworkingRequest request = new UpdateCoworkingRequest(ADDRESS);
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        coworkingEntity.setId(UUID);
        coworkingEntity.setAddress(ADDRESS);
        Coworking coworking = new Coworking(UUID, ADDRESS, new ArrayList<>());
        CoworkingResponse coworkingResponse = new CoworkingResponse(
                coworking.id().toString(), coworking.address(), new ArrayList<>());

        Mockito.when(coworkingMapper.dtoToModel(request)).thenReturn(coworking);
        Mockito.when(coworkingMapper.modelToDao(coworking)).thenReturn(coworkingEntity);
        Mockito.when(coworkingRepository.save(coworkingEntity)).thenReturn(coworkingEntity);
        Mockito.when(coworkingRepository.findById(UUID)).thenReturn(Optional.of(coworkingEntity));
        Mockito.when(coworkingMapper.daoToModelWithRooms(coworkingEntity)).thenReturn(coworking);
        Mockito.when(coworkingMapper.modelToDto(coworking)).thenReturn(coworkingResponse);

        assertEquals(coworkingResponse, uut.update(UUID.toString(), request));
    }

    @Test
    void getAll() {
        CoworkingEntity coworkingEntity = new CoworkingEntity();
        List<CoworkingEntity> coworkingEntityList = List.of(coworkingEntity);
        Coworking coworking = new Coworking(UUID, ADDRESS, new ArrayList<>());
        List<Coworking> coworkings = List.of(coworking);
        CoworkingResponse coworkingResponse = new CoworkingResponse(
                coworking.id().toString(), coworking.address(), new ArrayList<>());
        List<CoworkingResponse> coworkingResponses = List.of(coworkingResponse);

        Mockito.when(coworkingRepository.findAll()).thenReturn(coworkingEntityList);
        Mockito.when(coworkingMapper.daoToModelWithRooms(coworkingEntityList)).thenReturn(coworkings);
        Mockito.when(coworkingMapper.modelToDto(coworkings)).thenReturn(coworkingResponses);

        assertEquals(coworkingResponses, uut.getAll());
    }

    @Test
    void deleteById() {
        uut.deleteById(UUID.toString());

        Mockito.verify(coworkingRepository).deleteById(UUID);
    }
}