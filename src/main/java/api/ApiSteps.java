package api;

import models.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static config.Config.config;
import static io.restassured.RestAssured.given;

public class ApiSteps {

    @Step("Создаем задачу с заданным заголовком")
    public Issue createIssue(String title, String body) {
        final Issue toCreate = new Issue();
        toCreate.setTitle(title);
        toCreate.setBody(body);

        return given()
                .filter(new AllureRestAssured())
                .baseUri("https://api.github.com")
                .header("Authorization", "token " + config().getToken())
                .body(toCreate)
                .when()
                .post("/repos/test-pikabu/test_repo/issues")
                .then()
                .statusCode(201)
                .extract()
                .as(Issue.class);
    }

    @Step("Получаем задачу с GitHub")
    public Issue getIssue(int number) {
        return given()
                .filter(new AllureRestAssured())
                .baseUri("https://api.github.com")
                .header("Authorization", "token " + config().getToken())
                .when()
                .get("/repos/test-pikabu/test_repo/issues/" + number)
                .then()
                .statusCode(200)
                .extract()
                .as(Issue.class);
    }
}