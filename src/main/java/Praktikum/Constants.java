package Praktikum;

import static io.restassured.RestAssured.given;
public class Constants {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String CHANGE_USER = "/api/auth/user";
    public static final String ORDERS = "/api/orders";
    public static final String ALL_ORDERS = "/api/orders/all";
    public static final String INGREDIENTS = "/api/ingredients";

}
