package ru.akhilko.coworkingtest.model;

import java.util.List;

public record Coworking(String address, List<Room> rooms) {
}
