package user;

import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {
    private String accessToken;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }

    @DisplayName("User can logg in with correct params")
    @Description(" POST/api/auth/login")
    @Test
    public void canLoggInWithCorrectParams(){
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);

        accessToken = userClientAPI.getAccessToken(loginResponse);
    }
    @DisplayName("User can't logg in with wrong mail")
    @Description(" POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongMail(){
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        user.setEmail("0000");

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkNotLoggedInWithWrongParam(loginResponse);
    }
    @DisplayName("User can't logg in with wrong mail")
    @Description(" POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongPassword(){
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        user.setPassword("0000");

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkNotLoggedInWithWrongParam(loginResponse);
    }
}