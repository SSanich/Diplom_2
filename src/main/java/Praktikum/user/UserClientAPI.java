package Praktikum.user;

import static Praktikum.Constants.*;
import Praktikum.Model;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClientAPI extends Model {
    @Step("Create correct user")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(CREATE_USER_URL)
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
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("authorization", accessToken)
                .when()
                .delete(CHANGE_USER_URL)
                .then().log().all();
    }

    @Step("Login user")
    public ValidatableResponse loginUser(UserCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(LOGIN_USER_URL)
                .then().log().all();
    }

    @Step("Changing user data")
    public ValidatableResponse changeUserData(String accessToken, User user2) {
        return spec()
                .header("authorization", accessToken)
                .body(user2)
                .when()
                .patch(CHANGE_USER_URL)
                .then()
                .log().all();
    }

    @Step("Changing not auth user data")
    public ValidatableResponse changeUserDataWithoutAuth(User user2) {
        return spec()
                .body(user2)
                .when()
                .patch(CHANGE_USER_URL)
                .then()
                .log().all();
    }
}
