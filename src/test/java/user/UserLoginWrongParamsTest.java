package user;

import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import Praktikum.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
// тут наоборот параметризацию не применять, объединить  с логин тестом
public class UserLoginWrongParamsTest {
    private final UserClientAPI client = new UserClientAPI();
    UserCheck userCheck = new UserCheck();

    @DisplayName("User can logg in with incorrect params")
    @Test
    public void canNotLoggInWithWrongParams(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        userCheck.checkCreatedSuccessfully(createResponse);

        user.setPassword("0978");

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        userCheck.checkNotLoggedInWithoutRequiredField(loginResponse);
    }
}