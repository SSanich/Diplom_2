package order;

import Praktikum.order.Order;
import Praktikum.order.OrderCheck;
import Praktikum.order.OrderClientAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCreationWithoutAuthTest {
    private final OrderClientAPI orderClientAPI = new OrderClientAPI();
    OrderCheck orderCheck = new OrderCheck();


    @DisplayName("Creating order with correct ingredients without auth")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void createOrderCorrectHash(){
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClientAPI.CreateOrder(order);
        orderCheck.checkCreatedOrder(orderResponse);
    }

    @DisplayName("Creating order with incorrect ingredients without auth")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void createOrderIncorrectHash(){
        final String[] ingredients = {"61c0c5a71d1f820daaa6d", "61c0c5a71d1f81bdaaa6f", "61c0c5a71d1f81bdaaa72"};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClientAPI.CreateOrder(order);
        orderCheck.checkCreatedOrderBadHash(orderResponse);
    }

    @DisplayName("Creating order with no ingredients without auth")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void createOrderNoHash(){
        final String[] ingredients = {};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClientAPI.CreateOrder(order);
        orderCheck.checkCreatedOrderNoHash(orderResponse);
    }
}