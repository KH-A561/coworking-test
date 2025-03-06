package ru.akhilko.coworkingtest.service;


import ru.akhilko.coworkingtest.dto.CoworkingDto;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;

import java.util.List;

public interface CoworkingService {
    CoworkingDto getById(String coworkingId);

    CoworkingDto create(CreateCoworkingRequest request);

    List<CoworkingDto> getAll();

    CoworkingDto update(String coworkingId, UpdateCoworkingRequest request);

    void deleteById(String coworkingId);
}
