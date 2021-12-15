package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.System.setProperty;

@Configuration
class SeleniumBeans {
    private static final String DRIVER_EXECUTABLE_PROPERTY_KEY = "webdriver.gecko.driver";
    private static final String UBLOCK_FILE_NAME = "uBlock.xpi";
    private static final String DECENTRALEYES_FILE_NAME = "decentraleyes.xpi";
    @Value("${" + DRIVER_EXECUTABLE_PROPERTY_KEY + "}")
    private String pathToExecutable;

    @Bean
    WebDriver getSeleniumDriver() {
        setProperty(DRIVER_EXECUTABLE_PROPERTY_KEY, pathToExecutable);
        val profile = new FirefoxProfile();
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("network.dns.disablePrefetchFromHTTPS", false);
        profile.setPreference("network.predictor.enable-prefetch", false);

        val addonsDirectory = new File(pathToExecutable).getParentFile().getAbsolutePath()
            + File.separator + "addons";
        val addons = List.of(
            UBLOCK_FILE_NAME,
            DECENTRALEYES_FILE_NAME
        );
        val options = new FirefoxOptions();
        options.setProfile(profile);
        val driver = new FirefoxDriver(options);
        addons.stream()
            .map(fileName -> Paths.get(addonsDirectory, fileName))
            .forEach(driver::installExtension);
        return driver;
    }
}
