package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.samokat.client.OrderClient;
import ru.yandex.samokat.model.ColorType;

import static org.hamcrest.CoreMatchers.*;
import static ru.yandex.samokat.model.ColorType.BLACK;
import static ru.yandex.samokat.model.ColorType.GREY;
import ru.yandex.samokat.model.Order;
import ru.yandex.samokat.util.OrderUtils;

import static org.apache.http.HttpStatus.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    // FIXME: 22.12.2021 опять ссаный массив
    private final List<ColorType> colors;
    private final int expectedCode;
    private final Matcher expectedTrack;

    public CreateOrderTest(List<ColorType> colors, int expectedCode, Matcher expectedTrack){
        this.colors = colors;
        this.expectedCode = expectedCode;
        this.expectedTrack = expectedTrack;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData(){
        return Arrays.asList(new Object[][] {
                {Arrays.asList(BLACK), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY), SC_CREATED, notNullValue()},
                {Arrays.asList(GREY, BLACK), SC_CREATED, notNullValue()},
                {null, SC_CREATED, notNullValue()}
        });
    }

    @Test
    public void testCreateOrder() {
        OrderClient orderClient = new OrderClient();
        Order order = OrderUtils.buildRandomOrder(this.colors);

        ValidatableResponse responseOfCreating = orderClient.create(order);

        responseOfCreating.assertThat()
                .statusCode(this.expectedCode)
                .body("track", this.expectedTrack);
    }
}
