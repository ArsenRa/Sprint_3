import POJO.CourierCreate;
import Praktikum.Courier;
import ScooterApi.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class CourierCreateTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    int statusCode;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierCreate.getRandomCourier();
    }

    @After
    public void tearDown(){
        courierId = courierClient.login(courier.getCredentials()).extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    public void createCourierRetrunTrueTest(){
        ValidatableResponse createResponse = courierClient.create(courier);
        boolean responseOk = createResponse.log().all().extract().path("ok");

        assertThat("true", responseOk, equalTo(true));
    }

    @Test
    public void createCourierRetrun201Test(){
         ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.log().all().extract().statusCode();
        boolean responseOk = createResponse.log().all().extract().path("ok");

        assertThat("201", statusCode, equalTo(SC_CREATED));
        assertThat("true", responseOk, equalTo(true));
    }

    @Test
    public void createCourierWithReturn200Test() {
         ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.log().all().extract().statusCode();

        assertThat("200", statusCode, equalTo(SC_CREATED));

    }

}

