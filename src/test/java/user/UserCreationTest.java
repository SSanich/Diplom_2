package user;

import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserCreationTest {
    private String accessToken;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    User user;

    @Before
    public void setUp() {
        user = User.random();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }

    //  сервер возвращает код 200, что не совсем корректно, ожидаемо код 201 (Created).
    //
    @DisplayName("Successfully creation of unique user check")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createUserTest() {
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
    }

    @DisplayName("Can not create existing user")
    @Description(" POST/ api/ auth/ register")
    @Test
    public void createSameUserTest() {
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        //второй такой-же пользователь
          ValidatableResponse doubleCreateResponse = userClientAPI.createUser(user);
        userCheck.checkNotCreatedSameUser(doubleCreateResponse);
        accessToken = userClientAPI.getAccessToken(createResponse);
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