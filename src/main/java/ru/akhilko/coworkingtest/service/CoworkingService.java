package ru.akhilko.coworkingtest.service;


import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.response.CoworkingResponse;

import java.util.List;

public interface CoworkingService {
    CoworkingResponse getById(String coworkingId);

    CoworkingResponse create(CreateCoworkingRequest request);

    List<CoworkingResponse> getAll();

    CoworkingResponse update(String coworkingId, UpdateCoworkingRequest request);

    void deleteById(String coworkingId);
}
