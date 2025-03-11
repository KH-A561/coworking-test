package ru.akhilko.coworkingtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.response.CoworkingResponse;
import ru.akhilko.coworkingtest.service.CoworkingService;

import java.util.List;

/**
 * Coworking API
 **/
@RestController
@RequestMapping("/api/coworking")
@AllArgsConstructor
@Tag(name = "coworking", description = "Coworking API")
@Slf4j
public class CoworkingController {
    private final CoworkingService coworkingService;

    @Operation(summary = "Get coworking by coworkingId", tags = {"coworking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CoworkingResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid coworkingId supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Coworking not found", content = @Content)})
    @GetMapping("/{coworkingId}")
    public ResponseEntity<CoworkingResponse> readById(@PathVariable @NotBlank String coworkingId) {
        log.info("CoworkingController readById request {}", coworkingId);
        return ResponseEntity.ok().body(coworkingService.getById(coworkingId));
    }

    @Operation(summary = "Get all coworking spaces", tags = {"coworking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    })
    })
    @GetMapping
    public ResponseEntity<List<CoworkingResponse>> readAll() {
        log.info("CoworkingController readAll request");
        return ResponseEntity.ok().body(coworkingService.getAll());
    }

    @Operation(summary = "Create coworking space", tags = {"coworking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CoworkingResponse.class))
                    })
    })
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<CoworkingResponse> create(@RequestBody CreateCoworkingRequest request) {
        log.info("CoworkingController create request {}", request);
        return ResponseEntity.ok().body(coworkingService.create(request));
    }

    @Operation(summary = "Update coworking space", tags = {"coworking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CoworkingResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid coworkingId or request supplied",
                    content = @Content)
    })
    @PutMapping(path = "/{coworkingId}", consumes = {"application/json"})
    public ResponseEntity<CoworkingResponse> update(@PathVariable @NotBlank String coworkingId,
                                                    @RequestBody UpdateCoworkingRequest request) {
        log.info("CoworkingController update coworkingId {} request {}", coworkingId, request);
        return ResponseEntity.ok().body(coworkingService.update(coworkingId, request));
    }

    @Operation(summary = "Delete coworking space", tags = {"coworking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid coworkingId supplied", content = @Content)
    })
    @DeleteMapping("/{coworkingId}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotBlank String coworkingId) {
        log.info("CoworkingController deleteById request {}", coworkingId);
        coworkingService.deleteById(coworkingId);
        return ResponseEntity.ok().build();
    }
}
