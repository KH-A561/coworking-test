package ru.akhilko.coworkingtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.dto.response.RoomResponse;
import ru.akhilko.coworkingtest.service.RoomService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Room API
 **/
@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
@Tag(name = "room", description = "Room API")
@Slf4j
@Validated
public class RoomController {
    private RoomService roomService;

    @Operation(summary = "Get room by id", tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoomResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid roomId supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)})
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> readById(@PathVariable @PositiveOrZero Long roomId) {
        log.info("RoomController readById request {}", roomId);
        return ResponseEntity.ok().body(roomService.getById(roomId));
    }

    @Operation(summary = "Create room", tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoomResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid room request supplied", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody CreateRoomRequest request) {
        log.info("RoomController create request {}", request);
        return ResponseEntity.ok().body(roomService.create(request));
    }

    @Operation(summary = "Update room", tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoomResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid roomId or request supplied",
                    content = @Content)
    })
    @PutMapping(path = "/{roomId}", consumes = {"application/json"})
    public ResponseEntity<RoomResponse> update(@PathVariable @PositiveOrZero Long roomId,
                                               @RequestBody UpdateRoomRequest request) {
        log.info("RoomController update roomId {} request {}", roomId, request);
        return ResponseEntity.ok().body(roomService.update(roomId, request));
    }

    @Operation(summary = "Delete room space", tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid roomId supplied", content = @Content)
    })
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Object> deleteById(@PathVariable @PositiveOrZero Long roomId) {
        log.info("RoomController deleteById request {}", roomId);
        roomService.deleteById(roomId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get free rooms with filtering",
            description = "possible filtering criteria: FREE room has more than N places; is FREE in given time [X, Y)",
            tags = {"room"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments supplied", content = @Content)
    })
    @GetMapping(value = "/free")
    public ResponseEntity<List<RoomResponse>> readFreeWithOptionalFiltering(
            @RequestParam @NotNull Instant freeFrom,
            @RequestParam @NotNull Instant freeTo,
            @RequestParam Optional<Integer> atLeastPlaces) {
        log.info("RoomController readWithOptionalFiltering request {} {} {}", freeFrom, freeTo, atLeastPlaces);
        return ResponseEntity.ok().body(roomService.getFreeWithFiltering(freeFrom, freeTo, atLeastPlaces.orElse(0)));
    }
}
