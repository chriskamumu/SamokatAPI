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
import static org.apache.http.HttpStatus.*;

import java.awt.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final ColorType[] colors;
    private final int expectedCode;
    private final Matcher expectedTrack;

    public CreateOrderTest(ColorType[] colors, int expectedCode, Matcher expectedTrack){
        this.colors = colors;
        this.expectedCode = expectedCode;
        this.expectedTrack = expectedTrack;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData(){
        return Arrays.asList(new Object[][] {
                {new ColorType[]{BLACK}, SC_CREATED, notNullValue()},
                {new ColorType[]{GREY}, SC_CREATED, notNullValue()},
                {new ColorType[]{GREY, BLACK}, SC_CREATED, notNullValue()},
                {null, SC_CREATED, notNullValue()}
        });
    }

    @Test
    public void testCreateOrder() throws ParseException {
        OrderClient orderClient = new OrderClient();
        Order order = Order.getRandom(this.colors);

        ValidatableResponse responseOfCreating = orderClient.create(order);

        responseOfCreating.assertThat()
                .statusCode(this.expectedCode)
                .body("track", this.expectedTrack);
    }
}
