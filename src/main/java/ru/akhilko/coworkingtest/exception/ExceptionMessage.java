package ru.akhilko.coworkingtest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    ROOM_ENTITY_HAS_NOT_BEEN_FOUND("Room entity has not been found"),
    INVALID_TIME_PERIOD("Time period cannot have end before start"),
    COWORKING_NOT_FOUND("Coworking not found");

    private final String message;
}
