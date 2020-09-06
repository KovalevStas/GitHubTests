package config;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;

import static config.BrowserName.CHROME;
import static config.BrowserName.FIREFOX;

public class CustomWebDriverProvider {

    public void setupBrowser() {
        final WebDriverConfig config = ConfigFactory.newInstance().create(WebDriverConfig.class);


        if (config.remote()) {
//            new RemoteWebDriver(config.remoteUrl(), DesiredCapabilities.chrome());
            Configuration.remote = config.remoteUrl();
            Configuration.browser = "chrome";
        } else {
            if (CHROME.equals(config.browserName()))
                Configuration.browser = "chrome";
            else {
                if (FIREFOX.equals(config.browserName()))
                    Configuration.browser = "firefox";
                else {
                    throw new RuntimeException("Unknown browser name: " + config.browserName());
                }
            }
        }

    }
}
