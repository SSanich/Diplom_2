package user;

import praktikum.user.User;
import praktikum.user.UserCheck;
import praktikum.user.UserClientAPI;
import praktikum.user.UserCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
    private String accessToken;
    User user;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();

    @Before
    public void setUp() {
        user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }
// в документации не указан код ответа, фактически приходит ожидамый для такого кейса код 200
    @DisplayName("User can logg in with correct params")
    @Description(" POST/api/auth/login")
    @Test
    public void canLoggInWithCorrectParams() {
        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);
    }

    @DisplayName("User can't logg in with wrong mail")
    @Description(" POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongMail() {
        user.setEmail("0000");
        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkNotLoggedInWithWrongParam(loginResponse);
    }

    @DisplayName("User can't logg in with wrong mail")
    @Description(" POST/api/auth/login")
    @Test
    public void canNotLoggInWithWrongPassword() {
        user.setPassword("0000");
        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkNotLoggedInWithWrongParam(loginResponse);
    }
}