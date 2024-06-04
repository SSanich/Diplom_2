package user;

import Praktikum.user.UserCheck;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import Praktikum.user.User;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import org.junit.After;
import org.junit.Test;

public class ChangeUserDataWithAuthTest {
    private String accessToken;
    private final UserClientAPI client = new UserClientAPI();
    UserCheck userCheck = new UserCheck();

    @DisplayName("Changing data with authorization")
    @Test
    public void changeUserDataTest(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        userCheck.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);

        var user2 = new User(RandomStringUtils.randomAlphabetic(7) + "@gmail.com", "9873", "Jack");
        ValidatableResponse changeResponse =  client.changeUserData(accessToken, user2);
        userCheck.checkChanged(changeResponse);
    }

    @After
    public void tearDown(){
        client.deleteUser(accessToken);
    }
}