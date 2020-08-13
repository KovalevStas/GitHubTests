package test;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    @Step("Создаем задачу с заданным заголовком")
    public Issue createIssue(String title) {
        final Issue toCreate = new Issue();
        toCreate.setTitle(title);
        return given()
                .filter(new AllureRestAssured())
                .baseUri("https://api.github.com")
                .header("Authorization", "token 06a332d8e52818bf4c55c4d3338555e6da1ad5b1")
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
                .header("Authorization", "token 06a332d8e52818bf4c55c4d3338555e6da1ad5b1")
                .when()
                .get("/repos/test-pikabu/test_repo/issues/" + number)
                .then()
                .statusCode(200)
                .extract()
                .as(Issue.class);
    }
}