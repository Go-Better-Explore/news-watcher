package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static java.util.Objects.requireNonNull;

@Slf4j
@Configuration
class SeleniumBeans {
    @Bean
    WebDriver getSeleniumDriver() {
        val profile = new FirefoxProfile();
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("network.dns.disablePrefetchFromHTTPS", false);
        profile.setPreference("network.predictor.enable-prefetch", false);
        val options = new FirefoxOptions();
        options.setProfile(profile);
        val network = Network.newNetwork();
        val container = new BrowserWebDriverContainer<>()
            .withCapabilities(options)
            .withNetwork(network)
            .withLogConsumer(new Slf4jLogConsumer(log));
        container.start();
        return requireNonNull(container.getWebDriver());
    }
}
