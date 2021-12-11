package ru.gobetter.newswatcher.web.interaction.selenium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.openqa.selenium.remote.DesiredCapabilities.firefox;

@Configuration
class SeleniumBeans {
    @Bean
    WebDriver getSeleniumDriver() {
        Capabilities capabilities = firefox();
        return new FirefoxDriver(new FirefoxOptions(capabilities));
    }
}
