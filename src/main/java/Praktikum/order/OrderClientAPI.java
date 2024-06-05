package Praktikum.order;

import Praktikum.Model;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static Praktikum.Constants.*;
import static io.restassured.RestAssured.given;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class OrderClientAPI extends Model {
    @Step("Creating order")
    public ValidatableResponse CreateOrder(Order order) {
        return   spec()
                .body(order)
                .when()
                .post(ORDERS_URL)
                .then().log().all();
    }

    @Step("Getting orders from auth user")
    public ValidatableResponse getOrdersAuth(String accessToken) {
        return spec()
                .header("authorization", accessToken)
                .when()
                .get(ORDERS_URL)
                .then()
                .log().all();
    }

    @Step("Getting orders of not auth user")
    public ValidatableResponse getOrdersNoAuth() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS_URL)
                .then()
                .log().all();
    }
}
