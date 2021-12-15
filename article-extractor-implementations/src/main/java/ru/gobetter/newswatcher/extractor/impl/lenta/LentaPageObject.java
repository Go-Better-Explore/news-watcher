package ru.gobetter.newswatcher.extractor.impl.lenta;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class LentaPageObject implements CommonExtractorOperations {
    private final WebDriver driver;

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        var links = driver.findElements(By.cssSelector(".main-page a"));
        return links.stream()
            .map(link -> link.getAttribute("href"))
            .collect(toSet());
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        log.info("Extracting from article " + articleUrl);
        driver.navigate().to(articleUrl);

        val article = new Article();

        val titleElement = driver.findElement(By.cssSelector(".topic-header__title"));
        article.setHeadline(titleElement.getText());

        val contentElements = driver.findElements(By.cssSelector(".topic-body__content .topic-body__content-text"));
        article.setContent(contentElements.stream().map(WebElement::getText).collect(joining("\n")));

        return article;
    }
}
