package order;

import praktikum.ingredients.IngredientsAPI;
import praktikum.ingredients.OrderIngredients;
import praktikum.order.*;
import praktikum.user.User;
import praktikum.user.UserCheck;
import praktikum.user.UserClientAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class OrderCreationTest {
    private String accessToken;
    private OrderClientAPI orderClientAPI;
    private UserClientAPI userClientAPI;
    private OrderCheck orderCheck;
    private UserCheck userCheck;
    private User user;

    private IngredientsAPI ingredientsAPI;
    private List<String> ingredientIds;
    private Order order;


    @Before
    public void setUp() {
        orderClientAPI = new OrderClientAPI();
        userClientAPI = new UserClientAPI();
        ingredientsAPI = new IngredientsAPI();
        orderCheck = new OrderCheck();
        userCheck = new UserCheck();

        user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
        ingredientIds = new ArrayList<>();
        order = new Order(ingredientIds);
    }
    @After
    public void tearDown(){
        if (accessToken !=null){
            userClientAPI.deleteUser(accessToken);
        }
    }
// в документации не указан код ответа. Фактически возвращается 200, хотя должен 201(Created)
    @DisplayName("Creating order with correct ingredients")
    @Description("Get orders with auth user. POST/api/orders")
    @Test
    public void createOrderCorrectHashAuth(){
        OrderIngredients orderIngredients = ingredientsAPI.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse createOrderResponse = orderClientAPI.createOrderWithAuth(order,accessToken);
        orderCheck.checkCreatedOrderWithAuth(createOrderResponse);
    }
    @DisplayName("Creating order with correct ingredients without auth")
    @Description(" POST/api/orders")
    @Test
    public void createOrderCorrectHashNotAuth(){
        OrderIngredients orderIngredients = ingredientsAPI.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());
        ValidatableResponse orderResponse = orderClientAPI.createOrderWithoutAuth(order);
        orderCheck.checkCreatedOrderWithAuth(orderResponse);
    }

    //в документации допущена ошибка. Указано, что при передаче не валидного хеша возвращается ошибка 500
    //что не корректно. Должна возвращаться ошибка 400. В данном случае тест написан согласно документации и не падает
    // на боевом проекте, ситуацию неообходимо обсудить с разработчиками/аналитиками
    @DisplayName("Creating order with incorrect ingredients auth user")
    @Description(" POST/api/orders")
    @Test
    public void createOrderIncorrectHashAuth(){
        OrderIngredients orderIngredients = ingredientsAPI.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id()+"000");
        ingredientIds.add(orderIngredients.getData().get(1).get_id()+"000");
        ValidatableResponse orderResponse = orderClientAPI.createOrderWithAuth(order,accessToken);
        orderCheck.checkCreatedOrderBadHash(orderResponse);
    }
    @DisplayName("Creating order with incorrect ingredients not auth user")
    @Description(" POST/api/orders")
    @Test
    public void createOrderIncorrectHashNotAuth(){
        OrderIngredients orderIngredients = ingredientsAPI.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id()+"000");
        ingredientIds.add(orderIngredients.getData().get(1).get_id()+"000");
        ValidatableResponse orderResponse = orderClientAPI.createOrderWithoutAuth(order);
        orderCheck.checkCreatedOrderBadHash(orderResponse);
    }

    @DisplayName("Creating order with no ingredients auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderNoHashAuth(){
        ValidatableResponse orderResponse = orderClientAPI.createOrderWithAuth(order,accessToken);
        orderCheck.checkCreatedOrderNoHash(orderResponse);
    }
    @DisplayName("Creating order with no ingredients not auth user")
    @Description("POST/api/orders")
    @Test
    public void createOrderNoHashNotAuth(){
        ValidatableResponse orderResponse = orderClientAPI.createOrderWithoutAuth(order);
        orderCheck.checkCreatedOrderNoHash(orderResponse);
    }
}