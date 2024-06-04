package user;

import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClientAPI;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

// сделать параметризацию
public class UserCreationWithoutOneFieldTest {

    private final UserClientAPI client = new UserClientAPI();
    UserCheck userCheck = new UserCheck();

    @DisplayName("Can not create user without one required field")
    @Test
    public void createUserWithoutOneRequiredField(){
        var user = new User("","1234", "Jack");
        ValidatableResponse createResponse = client.createUser(user);
        userCheck.checkNotCreatedWithoutOneField(createResponse);
    }
}