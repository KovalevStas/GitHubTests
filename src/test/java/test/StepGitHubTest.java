package test;


import com.codeborne.selenide.logevents.SelenideLogger;
import config.CustomWebDriverProvider;
import config.WebDriverConfig;
import helpers.BasicSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import models.Issue;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Owner("KovalevStanislav")
@Feature("Тесты GitHub с использованием Steps класса")
public class StepGitHubTest {

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

    @BeforeAll
    static void initLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @BeforeEach
    void start() {
        CustomWebDriverProvider custom = new CustomWebDriverProvider();
        custom.setupBrowser();
    }

    @DisplayName("Создание Issue через UI с BasicSteps")
    @Test
    public void creatingIssueTestWithBasicSteps() {
        BasicSteps steps = new BasicSteps();
        steps.openSite(base_url);
        steps.autorise(login, password);
        steps.openSite(issues_link);
        number = steps.createIssue(title, body);
        issue = steps.getIssueFromGithub(number);
        steps.assertAddIssue(issue, title, login);
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }
}
