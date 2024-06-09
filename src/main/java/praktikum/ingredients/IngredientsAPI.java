package praktikum.ingredients;

import praktikum.Constants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static praktikum.Constants.BASE_URI;
import static io.restassured.RestAssured.given;

public class IngredientsAPI {
        @Step("Получение списка ингредиентов")
        public OrderIngredients getIngredients() {
            return given()
                    .contentType(ContentType.JSON)
                    .baseUri(BASE_URI)
                    .get(Constants.INGREDIENTS_URI)
                    .then().log().all()
                    .extract()
                    .body()
                    .as(OrderIngredients.class);
        }
    }

