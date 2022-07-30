package ScooterApi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class ApiClient {
    public static final String COURIER = "/courier";
    public static final String LOGIN = "/login";
    public static final String ORDER = "/orders";
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/api/v1";

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();

    }
}
