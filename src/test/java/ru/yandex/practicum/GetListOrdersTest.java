package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.OrderClient;
import ru.yandex.practicum.scooter.api.model.OrderList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.NewOrder.getRandomOrder;

@DisplayName("Получение списка заказов")
public class GetListOrdersTest {
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    public void test() {
        orderClient.createOrder(getRandomOrder());

        // Делаем действие
        Response response = orderClient.getOrders();

        // Проверка
        assertEquals(SC_OK, response.statusCode());
        OrderList orderList = response.as(OrderList.class);
        assertTrue(orderList.getOrders().size() > 0);
    }
}
