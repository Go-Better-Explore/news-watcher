package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.setProperty;

@Configuration
class SeleniumBeans {
    private static final String DRIVER_EXECUTABLE_PROPERTY_KEY = "webdriver.gecko.driver";
    @Value("${" + DRIVER_EXECUTABLE_PROPERTY_KEY + "}")
    private String pathToExecutable;

    @Bean
    WebDriver getSeleniumDriver() {
        setProperty(DRIVER_EXECUTABLE_PROPERTY_KEY, pathToExecutable);
        val options = new FirefoxOptions();
        return new FirefoxDriver(options);
    }
}
