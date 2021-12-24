package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.ColorType;

import static org.hamcrest.CoreMatchers.*;
import static ru.yandex.samokat.model.ColorType.BLACK;
import static ru.yandex.samokat.model.ColorType.GREY;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.util.OrderUtils;

import static org.apache.http.HttpStatus.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CreateOrderTest {

    @ParameterizedTest
    @MethodSource("generateTestData")
    public void testCreateOrder(List<ColorType> colors, int expectedCode, Matcher expectedTrack) {
        OrderClient orderClient = new OrderClient();
        Order order = OrderUtils.buildRandomOrder(colors);

        ValidatableResponse responseOfCreating = orderClient.create(order);

        responseOfCreating.assertThat()
                .statusCode(expectedCode)
                .body("track", expectedTrack);
    }

    static Collection<Object[]> generateTestData() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(BLACK), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY, BLACK), SC_CREATED, notNullValue()},
                {null, SC_CREATED, notNullValue()}
        });
    }
}
