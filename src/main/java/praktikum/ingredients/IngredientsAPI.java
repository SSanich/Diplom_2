package praktikum.ingredients;

import praktikum.Constants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import praktikum.Model;

import static praktikum.Constants.BASE_URI;
import static io.restassured.RestAssured.given;

public class IngredientsAPI   extends Model {
        @Step("Получение списка ингредиентов")
        public OrderIngredients getIngredients() {
            return spec()
                    .get(Constants.INGREDIENTS_URI)
                    .then().log().all()
                    .extract()
                    .body()
                    .as(OrderIngredients.class);
        }
    }

