package ru.yandex.samokat.client;

import io.restassured.response.ValidatableResponse;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient{

    private final static String COURIER_PATH = "/api/v1/courier";

    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    public ValidatableResponse delete(int courierId){

        String body = "{ \"id\": \"" + courierId + "\"}";
        return given()
                .spec(getBaseSpec())
                .body(body)
                .log().all()
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then();

    }
}
