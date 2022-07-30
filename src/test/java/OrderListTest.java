import ScooterApi.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderListTest {

    OrderClient orderClient;

    public OrderListTest(){
        orderClient = new OrderClient();
    }

    @Test
    public void ordersGetListTest(){
        ValidatableResponse response = orderClient.getOrders();
        int statusCode = response.extract().statusCode();
        ArrayList orders = response.extract().path("orders");


        assertThat("200", statusCode, equalTo(SC_OK));
        assertThat("orders", orders, notNullValue());
    }
}