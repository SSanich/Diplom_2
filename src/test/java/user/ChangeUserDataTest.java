package user;

import Praktikum.user.UserCheck;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import Praktikum.user.User;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserDataTest {
    private String accessToken;
    User user;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    @Before
    public void setUp(){
        user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);
        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);
        accessToken = userClientAPI.getAccessToken(loginResponse);
    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }
// в документации пробелы с получаеым кодом и передаваемыми данными. Основываясь на прдыдущем опыте, работает ожидамо
    @DisplayName("Changing user email  data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserEmailAuthTest(){
        user.setEmail("NewEmailLion@yandex.ru");
        ValidatableResponse changeResponse =  userClientAPI.changeUserData(accessToken, user);
        userCheck.checkChanged(changeResponse);
    }

    @DisplayName("Changing user password  data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserPasswordAuthTest(){
        user.setPassword("00000");
        ValidatableResponse changeResponse =  userClientAPI.changeUserData(accessToken, user);
        userCheck.checkChanged(changeResponse);
    }

    @DisplayName("Changing user name data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserNameAuthTest(){
        user.setPassword("LionLion");
        ValidatableResponse changeResponse =  userClientAPI.changeUserData(accessToken, user);
        userCheck.checkChanged(changeResponse);
    }
    @DisplayName("Changing data without authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserDataTest() {
        user.setEmail("NewLionEmail@yandex.ru");
        ValidatableResponse changeResponse = userClientAPI.changeUserDataWithoutAuth(user);
        userCheck.checkChangedWithoutAuth(changeResponse);
    }

}