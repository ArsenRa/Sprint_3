import POJO.CourierCreate;
import POJO.CourierLogin;
import Praktikum.Courier;
import ScooterApi.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierCreateDupTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    int statusCode;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierCreate.getRandomCourier();
        courierClient.create(courier);
    }

    @Test
    public void createTwoSameLoginTest(){
        courier.setPassword(courier.getPassword()+"NEW");
        courier.setFirstName(courier.getFirstName()+"NEW");
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Этот логин уже используется", statusCode, equalTo(SC_CONFLICT));
    }

    @Test
    public void createTwoCouriersWithSameCredentialsTest(){
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Этот логин уже используется", statusCode, equalTo(SC_CONFLICT));
    }

}
