package ru.akhilko.coworkingtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.akhilko.coworkingtest.dto.request.CreateReservationRequest;
import ru.akhilko.coworkingtest.dto.response.ReservationResponse;
import ru.akhilko.coworkingtest.service.ReservationService;

/**
 * Reservation API
 **/
@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
@Tag(name = "reservation", description = "Reservation API")
@Slf4j
@Validated
public class ReservationController {
    private ReservationService reservationService;


    @Operation(summary = "Create reservation", tags = {"reservation"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ReservationResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid reservation request supplied", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody CreateReservationRequest request) {
        log.info("ReservationController create request {}", request);
        return ResponseEntity.ok().body(reservationService.create(request));
    }
}
