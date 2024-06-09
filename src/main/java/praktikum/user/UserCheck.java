package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserCheck {
    @Step("Check user create ")
    public void checkCreatedSuccessfully(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Check user login successfully")
    public void checkLoggedInSuccessfully(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Check can not create same user")
    public void checkNotCreatedSameUser(ValidatableResponse createSecondUserResponse) {
        createSecondUserResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Check can not create user without required fields")
    public void checkNotCreatedWithoutOneField(ValidatableResponse createResponse) {
        createResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))

                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Check can not login with wrong params")
    public void checkNotLoggedInWithWrongParam(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Check of change user data")
    public void checkChanged(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .body("success", equalTo(true));
    }

    @Step("Check not auth user can not change data")
    public void checkChangedWithoutAuth(ValidatableResponse changeResponse) {
        changeResponse
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
