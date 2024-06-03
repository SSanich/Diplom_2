import Praktikum.user.User;
import Praktikum.user.UserCheck;
import Praktikum.user.UserClient;
import Praktikum.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {
    private String accessToken;
    private final UserClient client = new UserClient();

    @DisplayName("User can logg in with correct params")
    @Test
    public void canLoggInWithCorrectParams(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        UserCheck.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        UserCheck.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);
    }

    @After
    public void tearDown(){
        client.deleteUser(accessToken);
    }
}