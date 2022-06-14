package ru.yandex.practicum.scooter.api.model;

public class CreateCourierResponse {
    private Boolean ok;

    public CreateCourierResponse(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getOk() {
        return ok;
    }
}
