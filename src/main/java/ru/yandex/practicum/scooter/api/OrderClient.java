package ru.yandex.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.NewOrder;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient {
    @Step("Создание заказа")
    public Response createOrder(NewOrder newOrder) {
        return given()
                .spec(getReqSpec())
                .body(newOrder)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .spec(getReqSpec())
                .when()
                .get(BASE_URL + "/api/v1/orders");
    }
}