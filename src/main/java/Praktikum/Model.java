package Praktikum;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static Praktikum.Constants.BASE_URI;
import static io.restassured.RestAssured.given;

public class Model {
    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }
}
