package ru.akhilko.coworkingtest.model;

import java.util.List;
import java.util.UUID;

public record Coworking(UUID id, String address, List<Room> rooms) {
}
