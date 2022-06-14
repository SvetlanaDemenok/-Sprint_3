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

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

@DisplayName("Авторизация")
public class CourierLoginTest {
    Courier courier;
    CourierClient courierClient = new CourierClient();

    @Before
    public void setUp(){
        // Готовим данные
        courier = getRandomCourier();
        courierClient.createCourier(courier);
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
    @DisplayName("Авторизация с корректными логином и паролем")
    public void loginSuccessTest() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_OK, responseLogin.statusCode());
        int courierId = responseLogin.body().jsonPath().getInt("id");
        assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Авторизация с некорректным логином и корректным паролем")
    public void incorrectLoginTest() {
        CourierCredentials courierCredentials = new CourierCredentials("INCORRECT", courier.getPassword());

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Учетная запись не найдена", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Авторизация с корректным логином и некорректным паролем")
    public void incorrectPasswordTest() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), "INCORRECT");

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Учетная запись не найдена", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Авторизация с некорректным логином и некорректным паролем")
    public void nonExistentUserTest() {
        CourierCredentials courierCredentials = new CourierCredentials("INCORRECT", "INCORRECT");

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Учетная запись не найдена", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Авторизация с пустым логином и корректным паролем")
    public void emptyLoginTest() {
        CourierCredentials courierCredentials = new CourierCredentials("", courier.getPassword());

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для входа", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Авторизация с корректным логином и пустым паролем")
    public void emptyPasswordTest() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), "");

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для входа", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Авторизация с пустым логином и пустым паролем")
    public void emptyLoginPasswordTest() {
        CourierCredentials courierCredentials = new CourierCredentials("", "");

        // Делаем действие
        Response responseLogin = courierClient.login(courierCredentials);

        // Проверка
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        ErrorResponse errorResponse = responseLogin.as(ErrorResponse.class);
        assertEquals("Недостаточно данных для входа", errorResponse.getMessage());
    }
}
