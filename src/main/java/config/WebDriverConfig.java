package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;


@LoadPolicy(org.aeonbits.owner.Config.LoadType.MERGE)
@Sources({
        "system:properties",
        "classpath:config.properties",
        "file:C:\\local.properties"
})
public interface WebDriverConfig extends Config {
    @DefaultValue("CHROME")
    @Key("webdriver.browser.name")
    BrowserName browserName();

    @DefaultValue("CHROME")
    @Key("webdriver.browser.version")
    String browserVersion();

    @DefaultValue("false")
    @Key("webdriver.remote")
    boolean remote();

    @Key("webdriver.remote.url")
    String remoteUrl();

    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("title")
    String title();

    @Key("body")
    String body();

    @Key("repository")
    String repository();

    @Key("url")
    String url();

    @Key("video_storage_url")
    String video_storage_url();
}
