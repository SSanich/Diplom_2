package user;

import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreationTest {
    private String accessToken;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    @After
    public void tearDown(){
        userClientAPI.deleteUser(accessToken);
    }

    @DisplayName("Successfully creation of unique user check")
    @Test
    public void createUniqueUserTest(){
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
// логинимся, чтобы получить токен
        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);
        accessToken = userClientAPI.getAccessToken(loginResponse);
    }

    @DisplayName("Can not create existing user")
    @Test
    public void createSameUserTest(){
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


}