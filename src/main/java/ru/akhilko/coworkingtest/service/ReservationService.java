package ru.akhilko.coworkingtest.service;

import ru.akhilko.coworkingtest.dto.request.CreateReservationRequest;
import ru.akhilko.coworkingtest.dto.response.ReservationResponse;

public interface ReservationService {
    ReservationResponse create(CreateReservationRequest request);
}
