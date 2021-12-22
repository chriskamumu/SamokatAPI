package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.Order;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    private OrderClient orderClient;

    @Before
    public void setUp() throws ParseException {
        orderClient = new OrderClient();
        //создание заказа, чтобы список заказов точно был не пустым
        orderClient.create(Order.getRandom(null));
    }

    @Test
    public void testGetOrderListWithoutParametersReturnsNotEpmpyList(){
        ValidatableResponse response = orderClient.getOrderList();
        response.assertThat().body("orders", notNullValue());
    }
}
