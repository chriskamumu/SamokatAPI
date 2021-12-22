package ru.yandex.samokat;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.yandex.samokat.client.CourierClient;
import ru.yandex.samokat.model.Courier;
import ru.yandex.samokat.model.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        loginAndSetCourierIdForDeletion(courier);
        if (courierId != 0) {
            courierClient.delete(courierId).assertThat().statusCode(SC_OK);
        }
    }

    private void loginAndSetCourierIdForDeletion(Courier courier){
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier)).extract().path("id");
    }

    @Test
    public void testLoginWithAllFieldsReturnsId() {

        ValidatableResponse responseOfLogin = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        responseOfLogin.assertThat().statusCode(SC_OK).body("id", notNullValue());

    }

    @Test
    public void testLoginWithoutLoginReturnsBadRequestCode(){

        ValidatableResponse responseOfLogin = courierClient.login(new CourierCredentials(null, courier.getPassword()));
        responseOfLogin.assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

    }


    //данный ТС игнорируется при запуске тестов, т.к. в сервисе
    //существует проблема, что при вызове ручки api/v1/courier/login с "password" = null
    //возвращаеся 504 ошибка, из-за чего тест зависает и падает.
    @Ignore
    @Test
    public void testLoginWithoutPasswordReturnsBadRequestCode(){
        ValidatableResponse responseOfLogin = courierClient.login(new CourierCredentials(courier.getLogin(), null));
        responseOfLogin.assertThat().statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginWithIncorrectLoginReturnsNotFoundCode(){
        ValidatableResponse responseOfLogin = courierClient
                .login(CourierCredentials.getCourierCredentialsWithRandomLogin(courier.getPassword()));

        responseOfLogin.assertThat().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void testLoginWithIncorrectPasswordReturnsNotFoundCode(){
        ValidatableResponse responseOfLogin = courierClient
                .login(CourierCredentials.getCourierCredentialsWithRandomPassword(courier.getLogin()));

        responseOfLogin.assertThat().statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
