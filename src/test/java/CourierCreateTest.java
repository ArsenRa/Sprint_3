import POJO.CourierCreate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ScooterApi.ApiClient.BASE_URL;
import static ScooterApi.ApiClient.COURIER;

public class CourierCreateTest {
    static Integer id;

    @AfterClass
    public static void deleteCourierAfter() {
        String idCourier = Integer.toString(id);
        RestAssured.with()
                .contentType(ContentType.JSON)
                .log().all()
                .delete("http://qa-scooter.praktikum-services.ru/api/v1/courier/{idCourier}", idCourier)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    public void createTwoCouriersWithSameCredentialsTest(){
        CourierCreate courierCreate = CourierCreate.getRandomCourier();
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createTwoSameLoginTest(){
        CourierCreate courierCreate = CourierCreate.getRandomCourier();
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createWithoutFilledFieldsTest(){
        CourierCreate courierCreate = new CourierCreate(null, null, null);
        String error = "Недостаточно данных для создания учетной записи";
        String response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(error, response);
    }

    @Test
    public void createCourierRetrunTrueTest(){
        CourierCreate courierCreate = CourierCreate.getRandomCourier();
        boolean ok = given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");
        assertTrue(ok);
    }

    @Test
    public void createCourierRetrun201Test(){
        CourierCreate courierCreate = CourierCreate.getRandomCourier();
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .statusCode(201);
    }

    @Test
    public void createCourierWithRetur200Test() {
        CourierCreate courierCreate = CourierCreate.getRandomCourier();
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .log().all()
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", Matchers.is(true));
    }

    @Test
    public void createCourierWithoutLoginTest() {
        CourierCreate courierCreate = CourierCreate.getRandomCourierWithoutLogin();
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courierCreate)
                .when()
                .post(COURIER)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",Matchers.is( "Недостаточно данных для создания учетной записи"));
    }

}

