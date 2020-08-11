package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GitHubTests {

    final static String login = "test-pikabu";
    final static String password = "pikabu1234";

    @Test
    public void creatingIssueTest() {
        open("https://github.com/");
        $(byText("Sign in")).click();
        $("#login_field").setValue(login);
        $("#password").setValue(password).pressEnter();
        open("https://github.com/test-pikabu/test_repo/issues");
        $(by("data-hotkey","c")).click();
        $("#issue_title").setValue("test_title");
        $("#issue_body").setValue("test_body");
        $(withText("Submit new issue")).click();
        $(by("data-tab-item","issues-tab")).click();
        $("div.js-issue-row a").shouldHave(text("test_title"));
    }
}
