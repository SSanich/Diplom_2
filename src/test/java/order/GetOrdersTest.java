package order;

import Praktikum.order.Order;
import Praktikum.order.OrderCheck;
import Praktikum.order.OrderClientAPI;
import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrdersTest {
    private String accessToken;
    private OrderClientAPI orderClientAPI;
    private UserClientAPI userClientAPI;
    private OrderCheck orderCheck;
    private UserCheck userCheck;


    @Before
    public void setUp() {
        orderClientAPI = new OrderClientAPI();
        userClientAPI = new UserClientAPI();
        orderCheck = new OrderCheck();
        userCheck = new UserCheck();

        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);
        accessToken = userClientAPI.getAccessToken(loginResponse);
    }

    @After
    public void tearDown() {
        if (accessToken !=null){
            userClientAPI.deleteUser(accessToken);
        }
    }

    @DisplayName("getting orders auth user")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void getAuthUserOrders() {
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClientAPI.CreateOrder(order);
        orderCheck.checkCreatedOrder(orderResponse);

        ValidatableResponse listResponse = orderClientAPI.getOrdersAuth(accessToken);
        orderCheck.checkCorrectOrders(listResponse);
    }

    @DisplayName("get not auth user orders")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void getNotAuthUserOrders() {
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClientAPI.CreateOrder(order);
        orderCheck.checkCreatedOrder(orderResponse);

        ValidatableResponse listResponse = orderClientAPI.getOrdersAuth("");
        orderCheck.checkIncorrectOrders(listResponse);
    }
}
