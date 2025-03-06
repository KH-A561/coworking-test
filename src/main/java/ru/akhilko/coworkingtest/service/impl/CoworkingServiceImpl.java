package ru.akhilko.coworkingtest.service.impl;

import org.springframework.stereotype.Service;
import ru.akhilko.coworkingtest.dto.CoworkingDto;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.service.CoworkingService;

import java.util.List;

@Service
public class CoworkingServiceImpl implements CoworkingService {

    @Override
    public CoworkingDto getById(String coworkingId) {
        return null;
    }

    @Override
    public CoworkingDto create(CreateCoworkingRequest request) {
        return null;
    }

    @Override
    public List<CoworkingDto> getAll() {
        return List.of();
    }

    @Override
    public CoworkingDto update(String coworkingId, UpdateCoworkingRequest request) {
        return null;
    }

    @Override
    public void deleteById(String coworkingId) {

    }
}
