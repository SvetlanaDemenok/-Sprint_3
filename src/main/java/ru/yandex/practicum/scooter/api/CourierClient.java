package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseApiClient {
    public Response createCourier(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response login(CourierCredentials courierCredentials){
        return given()
                .spec(getReqSpec())
                .body(courierCredentials)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");

    }

    public Boolean deleteCourier(int courier){
        return given()
                .spec(getReqSpec())
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + courier)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
