import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static ScooterApi.ApiClient.*;

public class OrderListTest {

    @Test
    public void getOrderListTest() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get(ORDER)
                .then()
                .assertThat()
                .log().all()
                .body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

}


