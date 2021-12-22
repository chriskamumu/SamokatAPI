package ru.yandex.samokat.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private final static String COURIER_PATH = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders to create a new order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .log().all()
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Send GET request to /api/v1/orders to get order list")
    public ValidatableResponse getOrderList(){
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(COURIER_PATH)
                .then().log().all();
    }
}
