package ru.gobetter.newswatcher.web.interaction.selenium;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static org.testcontainers.containers.Network.newNetwork;

@Slf4j
public class SeleniumDriverFactory {
    private static final String HOST_ADDONS = "/home/alexander/programming/frameworks/webdrivers/addons/";
    private static final String CONTAINER_ADDONS = "/etc/newsreader/addons";

    public static BrowserWebDriverContainer createrNewContainer() {
        val profile = new FirefoxProfile();
        profile.setPreference("permissions.default.image", 2);
        profile.setPreference("network.dns.disablePrefetchFromHTTPS", false);
        profile.setPreference("network.predictor.enable-prefetch", false);
        profile.addExtension(new File(CONTAINER_ADDONS + File.separator + "decentraleyes.xpi"));
        profile.addExtension(new File(CONTAINER_ADDONS + File.separator + "uBlock.xpi"));
//        profile.addExtension(new File(CONTAINER_ADDONS));
        val options = new FirefoxOptions()
            .setProfile(profile);
        val chromeOptions = new ChromeOptions()
//            .addExtensions(new File(HOST_ADDONS + File.separator + "uBlock.zip"))
            ;

        val container = new BrowserWebDriverContainer<>()
            .withCapabilities(chromeOptions)
            .withNetwork(newNetwork())
            .withFileSystemBind(HOST_ADDONS, CONTAINER_ADDONS, BindMode.READ_WRITE)
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null)
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
