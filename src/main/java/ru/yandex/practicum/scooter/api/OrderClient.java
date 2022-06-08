package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.NewOrder;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient {
    public Response createOrder(NewOrder newOrder) {
        return given()
                .spec(getReqSpec())
                .body(newOrder)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    public Response getOrders() {
        return given()
                .spec(getReqSpec())
                .when()
                .get(BASE_URL + "/api/v1/orders");
    }
}