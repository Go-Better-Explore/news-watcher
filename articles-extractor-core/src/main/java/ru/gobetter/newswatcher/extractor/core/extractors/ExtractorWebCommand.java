package ru.gobetter.newswatcher.extractor.core.extractors;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.openqa.selenium.WebDriver;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.webinteraction.api.WebCommand;

import java.util.List;

@RequiredArgsConstructor
public class ExtractorWebCommand implements WebCommand<List<Article>> {
    private final WebsiteArticlesExtractor extractor;

    @Override
    public List<Article> execute(WebDriver driver) {
        extractor.setWebDriver(driver);
        val result = extractor.extractArticles();
        extractor.setWebDriver(null);
        return result;
    }
}
