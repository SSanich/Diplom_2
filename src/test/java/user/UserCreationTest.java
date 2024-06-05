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

public class UserCreationTest {
    private String accessToken;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }
// согласно документации сервер возвращает код 200, что не совсем корректно, ожидаемо код 201 (Created).
    // тест будет падать
    @DisplayName("Successfully creation of unique user check")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createUserTest() {
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
    }

    @DisplayName("Can not create existing user")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createSameUserTest() {
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        //второй такой-же пользователь
        ValidatableResponse createSecondUserResponse = userClientAPI.createUser(user);
        userCheck.checkNotCreatedSameUser(createSecondUserResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);
        accessToken = userClientAPI.getAccessToken(loginResponse);
    }

    @DisplayName("Can not create user without mail field")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createUserWithoutMailField() {
        var user = User.random();
        user.setEmail("");
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkNotCreatedWithoutOneField(createResponse);
    }

    @DisplayName("Can not create user without password field")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createUserWithoutPasswordField() {
        var user = User.random();
        user.setPassword("");
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkNotCreatedWithoutOneField(createResponse);
    }

    @DisplayName("Can not create user without name field")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createUserWithoutNameField() {
        var user = User.random();
        user.setName("");
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkNotCreatedWithoutOneField(createResponse);
    }
}