package ru.akhilko.coworkingtest.model;

import java.util.List;

public record Room(Coworking coworking, List<Place> places) {
}
