package Praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.junit.Assert.*;

public class OrderCheck {
    @Step("Check order create successfully")
    public static void checkCreatedOrder(ValidatableResponse orderResponse) {
        boolean created = orderResponse
                .extract()
                .path("success");
        assertTrue(created);
    }

    @Step("Check order not create without ingredients")
    public static void checkCreatedOrderNoHash(ValidatableResponse orderResponse) {
        boolean created = orderResponse
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check order not create with false hash ingredients")
    public static void checkCreatedOrderBadHash(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Check  body  orders request")
    public static void checkCorrectOrders(ValidatableResponse listResponse) {
        String listed = listResponse
                .extract().response().getBody().asString();
        assertNotNull(listed);
    }

    @Step("Check   for not auth user   body of orders request")
    public static void checkIncorrectOrders(ValidatableResponse listResponse) {
        listResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
