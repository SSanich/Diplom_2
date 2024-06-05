package order;

import Praktikum.order.OrderCheck;
import Praktikum.order.OrderClientAPI;
import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersTest {
    private String accessToken;
    private OrderClientAPI orderClientAPI;
    private UserClientAPI userClientAPI;
    private OrderCheck orderCheck;
    private UserCheck userCheck;
    private User user;


    @Before
    public void setUp() {
        orderClientAPI = new OrderClientAPI();
        userClientAPI = new UserClientAPI();
        orderCheck = new OrderCheck();
        userCheck = new UserCheck();

        user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
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
        ValidatableResponse listResponse = orderClientAPI.getOrdersAuthUser(accessToken);
        orderCheck.checkCorrectOrders(listResponse);
    }

    @DisplayName("get not auth user orders")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void getNotAuthUserOrders() {
        ValidatableResponse listResponse = orderClientAPI.getOrdersNotAuthUser();
        orderCheck.checkIncorrectOrders(listResponse);
    }
}
