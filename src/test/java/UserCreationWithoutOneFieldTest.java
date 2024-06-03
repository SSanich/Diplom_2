import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

// сделать параметризацию
public class UserCreationWithoutOneFieldTest {

    private final UserClient client = new UserClient();

    @DisplayName("Can not create user without one required field")
    @Test
    public void createUserWithoutOneRequiredField(){
        var user = new User("","1234", "Jack");
        ValidatableResponse createResponse = client.createUser(user);
        UserCheck.checkNotCreatedWithoutOneField(createResponse);
    }
}