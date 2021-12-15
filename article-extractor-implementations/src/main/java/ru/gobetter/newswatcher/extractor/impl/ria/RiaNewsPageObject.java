package ru.gobetter.newswatcher.extractor.impl.ria;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
class RiaNewsPageObject implements CommonExtractorOperations {
    private final WebDriver driver;

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector("#content .section__content .cell-list__list .cell-list__item.m-no-image a.cell-list__item-link"));
        return links.stream().map(link -> link.getAttribute("href")).collect(toSet());
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        val article = new Article();
        driver.navigate().to(articleUrl);

        val titleElement = driver.findElement(By.cssSelector("#content .article__title"));
        article.setHeadline(titleElement.getText());

        val contentBlocks = driver.findElements(By.cssSelector("#content .article__body .article__block"));
        article.setContent(contentBlocks.stream().map(WebElement::getText).collect(joining("\n")));

        article.setArticleDate(now());
        return article;
    }
}
