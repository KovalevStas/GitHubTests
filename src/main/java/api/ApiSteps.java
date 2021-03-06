package api;

import config.ApiDriverConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import models.Issue;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    final ApiDriverConfig config = ConfigFactory.newInstance().create(ApiDriverConfig.class);

    @Step("Создаем задачу с заданным заголовком")
    public Issue createIssue(String title, String body) {
        final Issue toCreate = new Issue();
        toCreate.setTitle(title);
        toCreate.setBody(body);

        return given()
                .filter(new AllureRestAssured())
                .baseUri(config.baseUri())
                .header("Authorization", "token " + config.token())
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
                .baseUri(config.baseUri())
                .header("Authorization", "token " + config.token())
                .when()
                .get("/repos/test-pikabu/test_repo/issues/" + number)
                .then()
                .statusCode(200)
                .extract()
                .as(Issue.class);
    }
}