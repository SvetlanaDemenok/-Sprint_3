package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.yandex.practicum.scooter.api.OrderClient;
import ru.yandex.practicum.scooter.api.model.NewOrder;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.NewOrder.getRandomOrder;

@RunWith(JUnitParamsRunner.class)
@DisplayName("Создание заказа")
public class CreateOrderTest {
    NewOrder newOrder;
    OrderClient orderClient = new OrderClient();

    @Before
    public void setUp(){
        // Готовим данные
        newOrder = getRandomOrder();
    }

    public static final String[][] colorsParams() {
        return new String[][] {
            {""},
            {"BLACK"},
            {"GRAY"},
            {"BLACK", "GRAY"},
        };
    }

    @Test
    @Parameters(method = "colorsParams")
    public void test(String[] colors) {
        if (colors.length == 1 && colors[0].equals("")) {
            colors = new String[0];
        }

        newOrder.setColor(Arrays.asList(colors));

        // Делаем действие
        Response response = orderClient.createOrder(newOrder);

        // Проверка
        assertEquals(SC_CREATED, response.statusCode());
        int track = response.body().jsonPath().getInt("track");
        assertTrue(track > 0);
    }
}
