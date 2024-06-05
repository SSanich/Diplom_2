package Praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCheck {
    @Step("Check order create successfully")
    public void checkCreatedOrderWithAuth(ValidatableResponse createOrderResponse) {
        createOrderResponse
                .assertThat().body("success", equalTo(true))
                .assertThat().body("order",notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Check order not create without ingredients")
    public void checkCreatedOrderNoHash(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("message",equalTo("Ingredient ids must be provided"));
    }

    @Step("Check order not create with false hash ingredients")
    public void checkCreatedOrderBadHash(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Check  body  orders request")
    public void checkCorrectOrders(ValidatableResponse listResponse) {
        listResponse
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
        ;
    }

    @Step("Check   for not auth user   body of orders request")
    public void checkIncorrectOrders(ValidatableResponse listResponse) {
        listResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message",equalTo("You should be authorised"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
