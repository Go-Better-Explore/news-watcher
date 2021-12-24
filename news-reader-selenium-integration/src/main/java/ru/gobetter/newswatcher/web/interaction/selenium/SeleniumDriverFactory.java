package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static org.testcontainers.containers.Network.newNetwork;

@Slf4j
public class SeleniumDriverFactory {
    public static BrowserWebDriverContainer createrNewContainer() {
        val profile = new FirefoxProfile();
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("network.dns.disablePrefetchFromHTTPS", false);
        profile.setPreference("network.predictor.enable-prefetch", false);
        val options = new FirefoxOptions()
            .setProfile(profile);

        val container = new BrowserWebDriverContainer<>()
            .withCapabilities(options)
            .withNetwork(newNetwork())
//            .withLogConsumer(new Slf4jLogConsumer(log))
            ;
        container.start();
        return container;
    }

    public static WebDriver createrNewDriver() {
        val container = createrNewContainer();
        return requireNonNull(container.getWebDriver());
    }

    public static Consumer<WebDriver> webDriverOnReleaseListener() {
        return WebDriver::close;
    }

    public static Consumer<BrowserWebDriverContainer> containerOnReleaseListener() {
        return container -> container.getWebDriver().close();
    }

}
