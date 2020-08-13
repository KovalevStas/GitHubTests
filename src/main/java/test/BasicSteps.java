package test;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.link;

public class BasicSteps {

    final static String login = "test-pikabu";
    final static String password = "pikabu1234";

    @Step("Открываем главную страницу")
    public void openSite(String url) {
        link("Используемая ссылка", url);
        open(url);
    }

    @Step("Авторизируемся")
    public void autorise(String login, String password) {
        $(byText("Sign in")).click();
        $("#login_field").setValue(login);
        $("#password").setValue(password).pressEnter();
    }

    @Step("Создаем новую Issue")
    public int createIssue(String title, String body) {
        $(by("data-hotkey", "c")).click();
        $("#issue_title").setValue(title);
        $("#issue_body").setValue(body);
        $(withText("Assignees")).click();
        $("div[data-filterable-for = 'assignee-filter-field'] label.select-menu-item").click();
        $(withText("Assignees")).click();
        $(".label-select-menu").click();
        $(byText("bug")).click();
        $(byText("invalid")).click();
        $(".label-select-menu").click();
        $(withText("Submit new issue")).click();
        return Integer.parseInt($(".gh-header-title span", 1).getText().replace("#", ""));
    }

    @Step("Загружаем созданную задачу через API")
    public Issue getIssueFromGithub(int number) {
        ApiSteps api = new ApiSteps();
        return api.getIssue(number);
    }

    @Step("Проверяем наличие новой Issue")
    public void assertAddIssue(Issue issue, String title, String assignee) {
        Assertions.assertEquals(issue.getTitle(), title);
        Assertions.assertEquals(issue.getAssignee().getLogin(),assignee);
        Assertions.assertEquals(issue.getLabels().get(0).getName(),"bug");
        Assertions.assertEquals(issue.getLabels().get(1).getName(),"invalid");
    }
}
