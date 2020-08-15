package test;

import api.ApiSteps;
import models.Issue;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static config.Config.config;
import static io.qameta.allure.Allure.link;
import static helpers.NamedBy.css;
import static helpers.NamedBy.named;

@Owner("KovalevStanislav")
@Feature("Тесты GitHub на чистом Selenide и Rest API")
public class ListenerGitHubTest {

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

    @BeforeAll
    static void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @DisplayName("Создание Issue через без Steps")
    @Test
    public void creatingIssueTestWithListener() {
        link("GitHub", base_url);
        link("Список задач", issues_link);

        open(base_url);

        $(byText("Sign in")).click();
        $("#login_field").setValue(login);
        $("#password").setValue(password).pressEnter();

        open(issues_link);
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
        number = Integer.parseInt($(".gh-header-title span", 1).getText().replace("#", ""));
        issue = api.getIssue(number);

        Assertions.assertEquals(issue.getTitle(), title);
        Assertions.assertEquals(issue.getAssignee().getLogin(),login);
        Assertions.assertEquals(issue.getLabels().get(0).getName(),"bug");
        Assertions.assertEquals(issue.getLabels().get(1).getName(),"invalid");

    }


    @DisplayName("Создание Issue через без Steps с NamedBy")
    @Test
    public void creatingIssueTestWithNamedBy() {
        link("GitHub", base_url);
        link("Список задач", issues_link);

        open(base_url);

        $(named(byText("Sign in")).as("Кнопка авторизации")).click();
        $(css("#login_field").as("Поле логина")).setValue(login);
        $(css("#password").as("Поле пароля")).setValue(password).pressEnter();

        open(issues_link);
        $(named(by("data-hotkey", "c")).as("Кнопка создания Issue")).click();
        $(css("#issue_title").as("Поле заголовка задачи")).setValue(title);
        $(css("#issue_body").as("Поле тела задачи")).setValue(body);
        $(named(withText("Assignees")).as("Пункт Assignees")).click();
        $(css("div[data-filterable-for = 'assignee-filter-field'] label.select-menu-item").as("Наименование исполнителя")).click();
        $(named(withText("Assignees")).as("Пункт Assignees")).click();
        $(css(".label-select-menu").as("Пункт Labels")).click();
        $(named(byText("bug")).as("Вариант bug")).click();
        $(named(byText("invalid")).as("Вариант invalid")).click();
        $(css(".label-select-menu").as("Пункт Labels")).click();
        $(named(withText("Submit new issue")).as("Кнопка подтверждения создания")).click();
        number = Integer.parseInt($(".gh-header-title span", 1).getText().replace("#", ""));
        issue = api.getIssue(number);

        Assertions.assertEquals(issue.getTitle(), title);
        Assertions.assertEquals(issue.getAssignee().getLogin(),login);
        Assertions.assertEquals(issue.getLabels().get(0).getName(),"bug");
        Assertions.assertEquals(issue.getLabels().get(1).getName(),"invalid");

    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }

}
