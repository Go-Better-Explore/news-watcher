package ru.gobetter.newswatcher.web.interaction.selenium.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.BrowserWebDriverContainer;
import ru.gobetter.newswatcher.web.interaction.selenium.SeleniumDriverFactory;

@Configuration
class WebDriverPoolConfiguration {
    private static final int CAPACITY = 2;

    @Bean
    Pool<BrowserWebDriverContainer> webDriverPool() {
        return LimitedPoolImpl.createPool(
            SeleniumDriverFactory::createrNewContainer,
            CAPACITY);
    }
}
