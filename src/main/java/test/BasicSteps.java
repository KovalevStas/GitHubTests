package test;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BasicSteps {

    final static String login = "test-pikabu";
    final static String password = "pikabu1234";

    @Step("Открываем главную страницу")
    public void openSite(String url) {
        open(url);
    }

    @Step("Авторизируемся")
    public void autorise(String login, String password) {
        $(byText("Sign in")).click();
        $("#login_field").setValue(login);
        $("#password").setValue(password).pressEnter();
    }

    @Step("Создаем новую Issue")
    public void createIssue(String title, String body) {

        $(by("data-hotkey", "c")).click();
        $("#issue_title").setValue(title);
        $("#issue_body").setValue(body);
        $(withText("Submit new issue")).click();
    }

    @Step("Проверяем наличие новой Issue")
    public void assertAddIssue(String title) {
        $(by("data-tab-item", "issues-tab")).click();
        $("div.js-issue-row a").shouldHave(text(title));
    }
}
