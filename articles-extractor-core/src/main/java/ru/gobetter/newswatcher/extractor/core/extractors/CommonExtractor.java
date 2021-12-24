package ru.gobetter.newswatcher.extractor.core.extractors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
class CommonExtractor implements WebsiteArticlesExtractor {
    private final CommonExtractorOperations pageObject;

    @Override
    public List<Article> extractArticles() {
        log.info("Extracting info from " + pageObject.getWebsite());
        val articleUrls = pageObject.getArticlesUrls(pageObject.getWebsite());
        val total = articleUrls.size();
        AtomicInteger current = new AtomicInteger();
        val result = articleUrls.stream()
            .peek(url ->
                log.info("[" + (current.incrementAndGet()) + "/" + total + "] " + "Visiting " + url)
            )
            .map(articleUrl -> {
                try {
                    return pageObject.extractInfoFromArticle(articleUrl);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .limit(5)
            .collect(toList());
        log.info("Done extracting from " + pageObject.getWebsite());
        return result;
    }

    @Override
    public void setWebDriver(WebDriver driver) {
        pageObject.setDriver(driver);
    }
}
