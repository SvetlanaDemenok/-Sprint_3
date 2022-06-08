package ru.yandex.practicum.scooter.api.model;

public class ErrorResponse {
    public String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
