package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

import static config.BrowserName.CHROME;
import static config.BrowserName.FIREFOX;

public class CustomWebDriver implements WebDriverProvider {

    final WebDriverConfig config = ConfigFactory.newInstance().create(WebDriverConfig.class);

    @Nonnull
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {



        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("videoFrameRate", 24);


        if (config.remote()) {
            capabilities.setBrowserName(String.valueOf(CHROME));
            capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
            WebDriverManager.chromedriver().setup();
            return new RemoteWebDriver(getRemoteWebdriverUrl(), capabilities);
        } else {
            if (CHROME.equals(config.browserName())) {
                capabilities.setBrowserName(String.valueOf(CHROME));
                capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                WebDriverManager.chromedriver().setup();
                Configuration.headless = true;
                return new ChromeDriver(capabilities);
            } else {
                if (FIREFOX.equals(config.browserName())) {
                    capabilities.setBrowserName(String.valueOf(FIREFOX));
                    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, getChromeOptions());
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver(capabilities);
                } else {
                    throw new RuntimeException("Unknown browser name: " + config.browserName());
                }
            }
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--lang=ru");
        return chromeOptions;
    }

    private URL getRemoteWebdriverUrl() {
        try {
            return new URL(config.remoteUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
