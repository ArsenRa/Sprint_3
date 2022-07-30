import Praktikum.Order;
import ScooterApi.OrderClient;


import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final String[] color;
    private OrderClient orderClient;
    private Order order;
    private int expectedStatusCode;

    public OrderCreateTest(String[] color, int expectedStatusCode) {
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;
    }


    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][] {
                {new String[]{"BLACK"}, 201},
                {new String[]{"CREY"}, 201},
                {new String[]{"CREY", "BLACK"}, 201},
                {new String[]{""}, 201}
        };
    }

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = Order.getRandomOrder();
        order.setColor(color);
    }

    @Test
    public void createOrderValidTest() {

        ValidatableResponse createResponse = orderClient.create(order);
        int statusCode = createResponse.extract().statusCode();
        int track = createResponse.extract().path("track");

        assertThat("Order is not created", statusCode, equalTo(expectedStatusCode));
        assertThat("No track number in response", track, greaterThan(0));

    }
}
