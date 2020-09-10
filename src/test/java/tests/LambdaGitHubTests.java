package tests;

import api.ApiSteps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import config.CustomWebDriver;
import config.WebDriverConfig;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import models.Issue;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.link;
import static io.qameta.allure.Allure.step;


@Owner("KovalevStanislav")
@Feature("Тесты GitHub с использованием лямбда функций")
public class LambdaGitHubTests extends TestBase{

    final WebDriverConfig config = ConfigFactory.newInstance().create(WebDriverConfig.class);

    final String
            login = config.login(),
            password = config.password(),
            base_url = config.url(),
            issues_link = config.url() + config.repository(),
            title = config.title(),
            body = config.body();
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
            $(".container").waitUntil(visible,5000);
        });

        step("Открываем список Issue", () -> {
            open(issues_link);
        });

        step("Создаем новую Issue", () -> {
            $(by("data-hotkey", "c")).waitUntil(visible,3000).click();
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
            Assertions.assertEquals(issue.getAssignee().getLogin(), login);
            Assertions.assertEquals(issue.getLabels().get(0).getName(), "bug");
            Assertions.assertEquals(issue.getLabels().get(1).getName(), "invalid");
        });
    }

}
