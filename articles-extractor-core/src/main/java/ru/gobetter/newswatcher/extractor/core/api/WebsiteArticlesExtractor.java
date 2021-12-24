package ru.gobetter.newswatcher.extractor.core.api;

import org.openqa.selenium.WebDriver;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

public interface WebsiteArticlesExtractor {
    List<Article> extractArticles();

    void setWebDriver(WebDriver driver);
}
