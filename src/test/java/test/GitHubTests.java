package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GitHubTests {

    final static String login = "test-pikabu";
    final static String password = "pikabu1234";
    final static String issues_link = "https://github.com/test-pikabu/test_repo/issues";

    @Test
    public void creatingIssueTest() {
        step("Открываем главную страницу", () -> {
            open("https://github.com/");
        });
        step("Авторизируемся", () -> {
            $(byText("Sign in")).click();
            $("#login_field").setValue(login);
            $("#password").setValue(password).pressEnter();
        });
        step("Открываем список Issue", () -> {
            open(issues_link);
        });
        step("Создаем новую Issue", () -> {
            $(by("data-hotkey","c")).click();
            $("#issue_title").setValue("test_title");
            $("#issue_body").setValue("test_body");
            $(withText("Submit new issue")).click();
        });
        step("Проверяем наличие новой Issue", () -> {
            $(by("data-tab-item","issues-tab")).click();
            $("div.js-issue-row a").shouldHave(text("test_title"));
        });
    }
}
