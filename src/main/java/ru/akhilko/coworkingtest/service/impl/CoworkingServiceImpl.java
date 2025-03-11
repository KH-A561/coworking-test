package ru.akhilko.coworkingtest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.response.CoworkingResponse;
import ru.akhilko.coworkingtest.entity.CoworkingEntity;
import ru.akhilko.coworkingtest.mapper.CoworkingMapper;
import ru.akhilko.coworkingtest.model.Coworking;
import ru.akhilko.coworkingtest.repo.CoworkingRepository;
import ru.akhilko.coworkingtest.service.CoworkingService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CoworkingServiceImpl implements CoworkingService {
    private final CoworkingRepository coworkingRepository;
    private final CoworkingMapper coworkingMapper;

    @Override
    public CoworkingResponse getById(String coworkingId) {
        return entityToResponseWithException(coworkingRepository.findById(UUID.fromString(coworkingId)));
    }

    @Override
    @Transactional
    public CoworkingResponse create(CreateCoworkingRequest request) {
        Coworking coworking = coworkingMapper.dtoToModel(request);
        CoworkingEntity coworkingEntity = coworkingMapper.modelToDao(coworking);

        if (coworkingEntity.getRooms() != null) {
            coworkingEntity.getRooms().forEach(r -> {
                r.setCoworking(coworkingEntity);
                Optional.ofNullable(r.getPlaces()).orElse(Collections.emptyList()).forEach(p -> p.setRoom(r));
                Optional.ofNullable(r.getReservations()).orElse(Collections.emptyList())
                        .forEach(re -> re.setRoom(r));
            });
        }

        return entityToResponseWithException(Optional.of(coworkingRepository.save(coworkingEntity)));
    }

    @Override
    public List<CoworkingResponse> getAll() {
        List<Coworking> coworkings = coworkingMapper.daoToModelWithRooms(coworkingRepository.findAll());
        return coworkingMapper.modelToDto(coworkings);
    }

    @Override
    @Transactional
    public CoworkingResponse update(String coworkingId, UpdateCoworkingRequest request) {
        Optional<CoworkingEntity> coworking = coworkingRepository.findById(UUID.fromString(coworkingId));

        if (coworking.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        CoworkingEntity coworkingEntity = coworking.get();
        if (!coworkingEntity.getAddress().equals(request.address())) {
            coworkingEntity.setAddress(request.address());
        }

        return entityToResponseWithException(Optional.of(coworkingRepository.save(coworkingEntity)));
    }

    @Override
    public void deleteById(String coworkingId) {
        coworkingRepository.deleteById(UUID.fromString(coworkingId));
    }

    private CoworkingResponse entityToResponseWithException(Optional<CoworkingEntity> coworkingEntity) {
        return coworkingEntity.map(coworkingMapper::daoToModelWithRooms)
                .map(coworkingMapper::modelToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }
}
