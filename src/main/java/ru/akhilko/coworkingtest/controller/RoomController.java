package ru.akhilko.coworkingtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.akhilko.coworkingtest.dto.RoomDto;
import ru.akhilko.coworkingtest.dto.request.CreateRoomRequest;
import ru.akhilko.coworkingtest.dto.request.UpdateRoomRequest;
import ru.akhilko.coworkingtest.service.RoomService;

import java.util.List;

@RestController("/room")
public class RoomController {
    private RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> readById(@PathVariable String roomId) {
        return ResponseEntity.ok().body(roomService.getById(roomId));
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> readAll() {
        return ResponseEntity.ok().body(roomService.getAll());
    }

    @PostMapping
    public ResponseEntity<RoomDto> create(@RequestBody CreateRoomRequest request) {
        return ResponseEntity.ok().body(roomService.create(request));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDto> update(@PathVariable String roomId,
                                               @RequestBody UpdateRoomRequest request) {
        return ResponseEntity.ok().body(roomService.update(roomId, request));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Object> deleteById(@PathVariable String roomId) {
        roomService.deleteById(roomId);
        return ResponseEntity.ok().build();
    }
}
