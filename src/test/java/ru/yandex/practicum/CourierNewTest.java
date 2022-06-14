package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.ErrorResponse;
import ru.yandex.practicum.scooter.api.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

@DisplayName("Создание курьера")
public class CourierNewTest {
    Courier courier;
    CourierClient courierClient = new CourierClient();

    @Before
    public void setUp(){
        // Готовим данные
        courier = getRandomCourier();
    }

    @After
    public void tearDown() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.login(courierCredentials);
        if (responseLogin.statusCode() == SC_OK) {
            int courierId = responseLogin.body().jsonPath().getInt("id");
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierTest() {
        // Делаем действие
        Response responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_CREATED, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getOk());
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void createDupCourierTest() {
        // Делаем действие
        Response responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_CREATED, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getOk());

        // Делаем действие еще раз
        responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_CONFLICT, responseCreate.statusCode());
        ErrorResponse errorResponse = responseCreate.as(ErrorResponse.class);
        assertEquals("Этот логин уже используется", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание курьера с пустым логином")
    public void createCourierWithEmptyLoginTest() {
        courier.setLogin("");

        // Делаем действие
        Response responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        ErrorResponse errorResponse = responseCreate.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для создания учетной записи", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание курьера с пустым паролем")
    public void createCourierWithEmptyPasswordTest() {
        courier.setPassword("");

        // Делаем действие
        Response responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        ErrorResponse errorResponse = responseCreate.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для создания учетной записи", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Создание курьера с пустым именем")
    public void createCourierWithEmptyFirstNameTest() {
        courier.setFirstName("");

        // Делаем действие
        Response responseCreate = courierClient.createCourier(courier);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        ErrorResponse errorResponse = responseCreate.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для создания учетной записи", errorResponse.getMessage());
    }
}
