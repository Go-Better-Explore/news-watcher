package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Value;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static org.testcontainers.containers.Network.newNetwork;

@Slf4j
class SeleniumBeans {
    private static final String ADDONS_DIRECTORY_WITHIN_CONTAINER = "/etc/newsreader/addons";
    private static final String DRIVER_EXECUTABLE_PROPERTY_KEY = "webdriver.gecko.driver";
    @Value("${" + DRIVER_EXECUTABLE_PROPERTY_KEY + "}")
    private String pathToExecutable;

    //    @Bean
    WebDriver getSeleniumDriver() {
        val profile = new FirefoxProfile();
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("network.dns.disablePrefetchFromHTTPS", false);
        profile.setPreference("network.predictor.enable-prefetch", false);

        val addonsDirectory = Paths.get(new File(pathToExecutable).getParent(), "addons").toFile();
        FileFilter filter = new ExtensionFilter("xpi");
        val addonFiles = addonsDirectory.listFiles(filter);
        /*
        Arrays.stream(addonFiles)
            .map(File::getName)
            .map(fileName -> Paths.get(ADDONS_DIRECTORY_WITHIN_CONTAINER, fileName).toFile())
            .forEach(profile::addExtension);
         */
        val options = new FirefoxOptions();
        options.setProfile(profile);

        val container = new BrowserWebDriverContainer<>()
            .withCapabilities(options)
            .withNetwork(newNetwork())
//            .withFileSystemBind(addonsDirectory.getAbsolutePath(), ADDONS_DIRECTORY_WITHIN_CONTAINER, READ_ONLY)
            .withLogConsumer(new Slf4jLogConsumer(log));
        container.start();
        return requireNonNull(container.getWebDriver());
    }
}
