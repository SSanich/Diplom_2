package Praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderCheck {
    @Step("Check order create successfully")
    public void checkCreatedOrder(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Check order not create without ingredients")
    public void checkCreatedOrderNoHash(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
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
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
