package Praktikum.order;

import Praktikum.Model;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static Praktikum.Constants.*;
import static io.restassured.RestAssured.given;

public class OrderClientAPI extends Model {
    @Step("Creating order with auth")
    public ValidatableResponse createOrderWithAuth(Order order, String accessToken) {
        return   spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS_URL)
                .then().log().all();
    }
    @Step("Creating order without auth")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return   spec()
                .body(order)
                .when()
                .post(ORDERS_URL)
                .then().log().all();
    }

    @Step("Getting orders of  auth user")
    public ValidatableResponse getOrdersAuthUser(String accessToken) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS_URL)
                .then()
                .log().all();
    }

    @Step("Getting orders of not auth user")
    public ValidatableResponse getOrdersNotAuthUser() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS_URL)
                .then()
                .log().all();
    }
}
