package Praktikum.order;

import Praktikum.Model;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static Praktikum.Constants.BASE_URI;
import static Praktikum.Constants.ORDERS;
import static io.restassured.RestAssured.given;


public class OrderClient  extends Model {
    @Step("Creating order")
    public ValidatableResponse CreateOrder(Order order) {
        return   spec()
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Getting orders from auth user")
    public ValidatableResponse getOrdersAuth(String accessToken) {
        return spec()
//                .header("authorization", accessToken)
                .when()
                .get(ORDERS)
                .then()
                .log().all();
    }

    @Step("Getting orders of not auth user")
    public ValidatableResponse getOrdersNoAuth() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ORDERS)
                .then()
                .log().all();
    }
}
