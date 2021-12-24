package ru.gobetter.newswatcher.extractor.core.extractors;

import org.openqa.selenium.WebDriver;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

public interface CommonExtractorOperations {
    void setDriver(WebDriver driver);

    String getWebsite();

    Set<String> getArticlesUrls(String mainPageUrl);

    Article extractInfoFromArticle(String articleUrl);
}
