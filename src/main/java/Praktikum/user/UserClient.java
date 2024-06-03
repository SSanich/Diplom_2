package Praktikum.user;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class UserClient {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    private static final String COURIER_AUTH_PATH = "/api/auth";

    @Step("Create correct user")
    public ValidatableResponse createUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(COURIER_AUTH_PATH+"/register")
                .then().log().all();
    }

    @Step("Get access token")
    public String getAccessToken(ValidatableResponse createResponse) {
        String accessToken;
        return accessToken = createResponse
                .extract()
                .path("accessToken");
    }

    @Step("Delete user")
    public void deleteUser(String accessToken) {
        given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization",accessToken)
                .baseUri(BASE_URI)
                .when()
                .delete(COURIER_AUTH_PATH+"/user")
                .then().log().all();
    }

    @Step("Login user")
    public ValidatableResponse loginUser(UserCredentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(creds)
                .when()
                .post(COURIER_AUTH_PATH+"/login")
                .then().log().all();
    }

    @Step("Changing user data")
    public ValidatableResponse changeUserData(String accessToken, User user2) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", accessToken)
                .baseUri(BASE_URI)
                .body(user2)
                .when()
                .patch(COURIER_AUTH_PATH + "/user")
                .then()
                .log().all();
    }

    @Step("Changing not auth user data")
    public ValidatableResponse changeUserDataWithoutAuth(User user2) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user2)
                .when()
                .patch(COURIER_AUTH_PATH + "/user")
                .then()
                .log().all();
    }
}
