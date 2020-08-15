package test;

import api.ApiSteps;
import models.Issue;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.link;
import static io.qameta.allure.Allure.step;
import static config.Config.config;


@Owner("KovalevStanislav")
@Feature("Тесты GitHub с использованием лямбда функций")
public class LambdaGitHubTests {

    final static String
            login = config().getLogin(),
            password = config().getPassword(),
            base_url = config().getLoginFormUrl(),
            issues_link = config().getLoginFormUrl() + config().getRepository(),
            title = config().getTitle(),
            body = config().getBody();
    private int number;
    private Issue issue = new Issue();
    private final ApiSteps api = new ApiSteps();


    @DisplayName("Создание Issue через UI без BasicSteps")
    @Test
    public void creatingIssueTest() {
        link("GitHub", base_url);
        link("Список задач", issues_link);

        step("Открываем главную страницу", () -> {
            open(base_url);
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
            $(by("data-hotkey", "c")).click();
            $("#issue_title").setValue(title);
            $("#issue_body").setValue(body);
        });

        step("Добавляем исполнителя", () -> {
            $(withText("Assignees")).click();
            $("div[data-filterable-for = 'assignee-filter-field'] label.select-menu-item").click();
            $(withText("Assignees")).click();
        });

        step("Добавляем теги", () -> {
            $(".label-select-menu").click();
            $(byText("bug")).click();
            $(byText("invalid")).click();
            $(".label-select-menu").click();
        });

        step("Подтверждаем создание задачи", () -> {
            $(withText("Submit new issue")).click();
            number = Integer.parseInt($(".gh-header-title span", 1).getText().replace("#", ""));
                });

        step("Загружаем созданную задачу через API", () -> {
            issue = api.getIssue(number);
        });

        step("Проверяем корректность созданной задачи", () -> {
            Assertions.assertEquals(issue.getTitle(), title);
            Assertions.assertEquals(issue.getAssignee().getLogin(),login);
            Assertions.assertEquals(issue.getLabels().get(0).getName(),"bug");
            Assertions.assertEquals(issue.getLabels().get(1).getName(),"invalid");
        });
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }
}
