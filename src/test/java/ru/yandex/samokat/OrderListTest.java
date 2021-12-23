package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.util.OrderUtils;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        orderClient.create(OrderUtils.buildRandomOrder(null));
    }

    @Test
    public void testGetOrderListWithoutParametersReturnsNotEpmpyList(){
        ValidatableResponse response = orderClient.getOrderList();
        response.assertThat().body("orders", notNullValue());
    }
}
