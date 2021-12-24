package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.util.OrderUtils;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    private OrderClient orderClient;

    @BeforeEach
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
