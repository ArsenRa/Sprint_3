package ScooterApi;

import Praktikum.Order;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class OrderClient extends ApiClient {


    @Step("Create order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Get orders list")
    public ValidatableResponse getOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER)
                .then();
    }
}