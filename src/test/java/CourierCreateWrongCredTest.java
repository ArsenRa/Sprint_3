import POJO.CourierCreate;
import Praktikum.Courier;
import ScooterApi.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class CourierCreateWrongCredTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;
    int statusCode;

    public CourierCreateWrongCredTest() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        if (statusCode == SC_CREATED) {
            courierId = courierClient.login(courier.getCredentials()).extract().path("id");
            courierClient.delete(courierId);
        }
    }

    @Test
    public void createWithoutFilledFieldsTest(){
        courier = CourierCreate.getRandomCourierWithEmptyFields();
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Недостаточно данных для создания учетной записи", statusCode, equalTo(SC_BAD_REQUEST));

    }

    @Test
    public void createCourierWithoutLoginTest() {
        courier = CourierCreate.getRandomCourierWithoutLogin();
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();

        assertThat("Недостаточно данных для создания учетной записи", statusCode, not(equalTo(SC_CREATED)));
        assertThat("400", statusCode, equalTo(SC_BAD_REQUEST));
    }


}
