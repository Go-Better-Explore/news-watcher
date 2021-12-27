package ru.gobetter.newswatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gobetter.webinteraction.api.WebInteractionExecutor;
import ru.gobetter.webinteraction.engines.selenium.WebInteractionEngines;

@Configuration
class WebInteractionConfiguration {
    @Bean
    WebInteractionExecutor webInteractionExecutor() {
        return WebInteractionEngines.createContainerEngine(12);
    }
}
