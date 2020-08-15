package test;

import models.Issue;
import helpers.BasicSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static config.Config.config;

@Owner("KovalevStanislav")
@Feature("Тесты GitHub с использованием Steps класса")
public class StepGitHubTest {

    final static String
            login = config().getLogin(),
            password = config().getPassword(),
            base_url = config().getLoginFormUrl(),
            issues_link = config().getLoginFormUrl() + config().getRepository(),
            title = config().getTitle(),
            body = config().getBody();
    private int number;
    private Issue issue = new Issue();

    @DisplayName("Создание Issue через UI с BasicSteps")
    @Test
    public void creatingIssueTestWithBasicSteps() {
        BasicSteps steps = new BasicSteps();
        steps.openSite(base_url);
        steps.autorise(login,password);
        steps.openSite(issues_link);
        number = steps.createIssue(title,body);
        issue = steps.getIssueFromGithub(number);
        steps.assertAddIssue(issue,title,login);
    }

    @AfterEach
    public void closeBrowser() {
        closeWebDriver();
    }
}
