import POJO.CourierCreate;
import POJO.CourierLogin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ScooterApi.ApiClient.*;

public class CourierLoginTest {
    static Integer id;

    @BeforeClass
    public static void createCourierBefore() {
        CourierCreate courierCreate  = new CourierCreate("Donut", "12345", "Homer");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);
    }
    @AfterClass
    public static void deleteCourierAfter() {
        String idCourier = Integer.toString(id);
        RestAssured.with()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .delete("http://qa-scooter.praktikum-services.ru/api/v1/courier/{idCourier}", idCourier)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    public void courierLoginWithValidDataTest() {
        CourierLogin courierLogin = new CourierLogin("Donut", "12345");
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .post(LOGIN);

        response.then().log().all().assertThat().body("id", notNullValue()).and().statusCode(SC_OK);
    }

    @Test
    public void courierLoginWhithValidIDTest() {
        CourierLogin courierLogin = new CourierLogin("Donut", "12345");
        id = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("id");
    }

    //тест с дефектом
    @Test
    public void courierLoginWhithOnlyLoginTest() {
        CourierLogin courierLogin = new CourierLogin("Donut", null);
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    public void courierLoginWithOnlyPasswordTest() {
        CourierLogin courierLogin = new CourierLogin(null, "EMS01");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .and()
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }

    @Test
    public void courierLoginWhithoutAccountTest() {
        CourierLogin courierLogin = new CourierLogin("Lisa", "Jazz");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .when()
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

    @Test
    public void courierWrongLoginAndPasswordTest() {
        CourierLogin courierLogin = new CourierLogin("Nelson", "EMS01");
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierLogin)
                .post(LOGIN)
                .then()
                .log().all()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", Matchers.is("Учетная запись не найдена"));
    }

}

