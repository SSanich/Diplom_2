import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClient;
import Praktikum.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
// тут наоборот параметризацию не применять, объединить  с логин тестом
public class UserLoginWrongParamsTest {
    private final UserClient client = new UserClient();

    @DisplayName("User can logg in with incorrect params")
    @Test
    public void canNotLoggInWithWrongParams(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        UserCheck.checkCreatedSuccessfully(createResponse);

        user.setPassword("0978");

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        UserCheck.checkNotLoggedInWithWrongParams(loginResponse);
    }
}