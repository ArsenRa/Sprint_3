import POJO.CourierCreate;
import POJO.CourierLogin;
import Praktikum.Courier;
import ScooterApi.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class CourierLoginTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierCreate.getRandomCourier();
        courierClient.create(courier);
    }

    @After
    public void tearDown() throws Exception{
        courierClient.delete(courierId);
    }

   @Test
    public void courierLoginWithValidDataTest() {
       ValidatableResponse loginResponse = courierClient.login(courier.getCredentials());
       int statusCode = loginResponse.log().all().extract().statusCode();
       courierId = loginResponse.log().all().extract().path("id");

       assertThat("200", statusCode, equalTo(SC_OK));
       assertThat("id", courierId, greaterThan(0));
    }

    //тест с дефектом
    @Test
    public void courierLoginWhithOnlyLoginTest() {
        CourierLogin credentialsWithoutLogin = courier.getCredentials();
        credentialsWithoutLogin.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(credentialsWithoutLogin);
        int statusCode = loginResponse.extract().statusCode();

        assertThat("200", statusCode, not(equalTo(SC_OK)));
        assertThat("Недостаточно данных для входа", statusCode, equalTo(SC_BAD_REQUEST));
    }

    @Test
    public void courierLoginWithOnlyPasswordTest() {
        CourierLogin credentialsWithoutPassword = courier.getCredentials();
        credentialsWithoutPassword.setPassword(null);
        ValidatableResponse loginResponse = courierClient.login(credentialsWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertThat("200", statusCode, not(equalTo(SC_OK)));
        assertThat("Недостаточно данных для входа", statusCode, equalTo(SC_BAD_REQUEST));
    }

    @Test
    public void courierLoginWhithoutAccountTest() {
        courier = CourierCreate.getRandomCourier();
        ValidatableResponse loginResponse = courierClient.login(courier.getCredentials());
        int statusCode = loginResponse.extract().statusCode();

        //assertThat("404", statusCode, not(equalTo(SC_OK)));
        assertThat("Учетная запись не найдена", statusCode, equalTo(SC_NOT_FOUND));
    }

    @Test
    public void courierWrongLoginAndPasswordTest() {
        CourierLogin credentialsWrongPasswordAndLogin = courier.getCredentials();
        credentialsWrongPasswordAndLogin.setPassword("Nelson");
        credentialsWrongPasswordAndLogin.setPassword("1233409");
        ValidatableResponse loginResponse = courierClient.login(credentialsWrongPasswordAndLogin);
        int statusCode = loginResponse.extract().statusCode();

        assertThat("Учетная запись не найдена", statusCode, equalTo(SC_NOT_FOUND));
    }

}

