package ru.gobetter.newswatcher.web.interaction.api;

import org.openqa.selenium.WebDriver;

public interface WebCommand<R> {
    R execute(WebDriver driver);
}
