package Praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserCheck {
    @Step("Check user create ")
    public static void checkCreatedSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(created);
    }

    @Step("Check user login successfully")
    public static void checkLoggedInSuccessfully(ValidatableResponse loginResponse) {
        boolean logged = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(logged);
    }

    @Step("Check can not create same user")
    public static void checkNotCreatedSameUser(ValidatableResponse createSecondUserResponse) {
        boolean created = createSecondUserResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check can not create user without required fields")
    public static void checkNotCreatedWithoutOneField(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check can not login with wrong params")
    public static void checkNotLoggedInWithWrongParams(ValidatableResponse loginResponse) {
        boolean logged = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(logged);
    }

    @Step("Check of change user data")
    public static void checkChanged(ValidatableResponse changeResponse) {
        boolean changed = changeResponse
                .extract()
                .path("success");
        assertTrue(changed);
    }

    @Step("Check not auth user can not change data")
    public static void checkChangedWithoutAuth(ValidatableResponse changeResponse) {
        boolean changed = changeResponse
                .extract()
                .path("success");
        assertFalse(changed);
    }
}
