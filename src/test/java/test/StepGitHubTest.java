package test;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Owner("KovalevStanislav")
@Feature("Тесты GitHub с использованием Steps класса")
public class StepGitHubTest {

    final static String
            login = "test-pikabu",
            password = "pikabu1234",
            base_url = "https://github.com",
            issues_link = String.format("%s/test-pikabu/test_repo/issues", base_url),
            title = "test_title",
            body = "test_body";
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
}
