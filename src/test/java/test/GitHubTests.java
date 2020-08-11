package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.link;
import static io.qameta.allure.Allure.step;
import static test.NamedBy.css;
import static test.NamedBy.named;


@Owner("KovalevStanislav")
@Feature("Тесты GitHub")
public class GitHubTests {

    final static String
            login = "test-pikabu",
            password = "pikabu1234",
            base_url = "https://github.com",
            issues_link = String.format("%s/test-pikabu/test_repo/issues", base_url),
            title = "test_title",
            body = "test_body";


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
            $(withText("Submit new issue")).click();
        });

        step("Проверяем наличие новой Issue", () -> {
            $(by("data-tab-item", "issues-tab")).click();
            $("div.js-issue-row a").shouldHave(text(title));
        });
    }

    @DisplayName("Создание Issue через UI с BasicSteps")
    @Test
    public void creatingIssueTestWithBasicSteps() {
        BasicSteps steps = new BasicSteps();
        steps.openSite(base_url);
        steps.autorise(login,password);
        steps.openSite(issues_link);
        steps.createIssue(title,body);
        steps.assertAddIssue(title);
    }


    @BeforeEach
    public void initLogger(){
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
            $(withText("Submit new issue")).click();

            $(by("data-tab-item", "issues-tab")).click();
            $("div.js-issue-row a").shouldHave(text(title));

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
        $(named(withText("Submit new issue")).as("Кнопка подтверждения создания")).click();

        $(named(by("data-tab-item", "issues-tab")).as("Вкладка Issue")).click();
        $(css("div.js-issue-row a").as("Первая задача в списке задач")).shouldHave(text(title));

    }

    @AfterEach
    public void closeBrowser (){
        closeWebDriver();
    }
}
