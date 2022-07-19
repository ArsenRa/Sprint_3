
import io.restassured.http.ContentType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;

import static org.hamcrest.Matchers.notNullValue;
import static ScooterApi.ApiClient.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    File json;

    public OrderCreateTest(File json){
        this.json = json;
    }


    @Parameterized.Parameters
    public static Object[] getSumDate(){
        return new Object[][] {
                {new File("src/test/resources/orderBlackColor.json")},
                {new File("src/test/resources/orderTwoColors.json")},
                {new File("src/test/resources/orderNoColor.json")}
        };
    }

    @Test
    public void createNewOrderTest(){

                given()
                        .contentType(ContentType.JSON)
                        .log().all()
                        .body(json)
                        .when()
                        .post(BASE_URL + ORDER)
                        .then()
                        .assertThat()
                        .body("track", notNullValue())
                        .and()
                        .statusCode(SC_CREATED);

    }
}
