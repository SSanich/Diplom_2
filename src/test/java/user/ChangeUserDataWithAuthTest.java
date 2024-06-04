package user;

import Praktikum.user.UserCheck;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import Praktikum.user.User;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


public class ChangeUserDataWithAuthTest {
    private String accessToken;
    private final UserClientAPI userClientAPI = new UserClientAPI();
    UserCheck userCheck = new UserCheck();
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClientAPI.deleteUser(accessToken);
        }
    }

    @DisplayName("Changing email user data with authorization")
    @Description("Path = api/auth/user")
    @Test
    public void changeUserEmailAuthTest(){
        var user = User.random();
        ValidatableResponse createResponse = userClientAPI.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClientAPI.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);

        accessToken = userClientAPI.getAccessToken(loginResponse);

        user.setEmail("NewEmailLion@yandex.ru");
        ValidatableResponse changeResponse =  userClientAPI.changeUserData(accessToken, user);
        userCheck.checkChanged(changeResponse);
    }


}