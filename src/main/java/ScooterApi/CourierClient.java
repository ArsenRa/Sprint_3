package ScooterApi;
import POJO.CourierLogin;
import Praktikum.Courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ApiClient{

    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierLogin courierLogin){
        return given()
                .spec(getBaseSpec())
                .body(courierLogin).log().all()
                .when()
                .post(COURIER + LOGIN)
                .then();
    }

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier).log().all()
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId){
        return given()
                .spec(getBaseSpec()).log().all()
                .when()
                .delete(COURIER + ":" + courierId)
                .then();
    }
}
