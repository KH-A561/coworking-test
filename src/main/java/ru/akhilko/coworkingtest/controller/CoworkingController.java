package ru.akhilko.coworkingtest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akhilko.coworkingtest.dto.CoworkingDto;
import ru.akhilko.coworkingtest.dto.request.CreateCoworkingRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateCoworkingRequest;
import ru.akhilko.coworkingtest.service.CoworkingService;

import java.util.List;

/**
 *
 **/
@RestController("/coworking")
@AllArgsConstructor
public class CoworkingController {
    private CoworkingService coworkingService;

    @GetMapping("/{coworkingId}")
    public ResponseEntity<CoworkingDto> readById(@PathVariable String coworkingId) {
        return ResponseEntity.ok().body(coworkingService.getById(coworkingId));
    }

    @GetMapping
    public ResponseEntity<List<CoworkingDto>> readAll() {
        return ResponseEntity.ok().body(coworkingService.getAll());
    }

    @PostMapping
    public ResponseEntity<CoworkingDto> create(@RequestBody CreateCoworkingRequest request) {
        return ResponseEntity.ok().body(coworkingService.create(request));
    }

    @PutMapping("/{coworkingId}")
    public ResponseEntity<CoworkingDto> update(@PathVariable String coworkingId,
                                               @RequestBody UpdateCoworkingRequest request) {
        return ResponseEntity.ok().body(coworkingService.update(coworkingId, request));
    }

    @DeleteMapping("/{coworkingId}")
    public ResponseEntity<Object> deleteById(@PathVariable String coworkingId) {
        coworkingService.deleteById(coworkingId);
        return ResponseEntity.ok().build();
    }
}
