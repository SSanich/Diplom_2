package Praktikum;

import static io.restassured.RestAssured.given;
public class Constants {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String CREATE_USER_URL = "/api/auth/register";
    public static final String LOGIN_USER_URL = "/api/auth/login";
    public static final String CHANGE_USER_URL = "/api/auth/user";
    public static final String ORDERS_URL = "/api/orders";
    public static final String ALL_ORDERS_URL = "/api/orders/all";
    public static final String INGREDIENTS_URL = "/api/ingredients";

}
