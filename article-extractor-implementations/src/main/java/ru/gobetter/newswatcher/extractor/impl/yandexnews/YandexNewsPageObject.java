package ru.gobetter.newswatcher.extractor.impl.yandexnews;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Service
@Qualifier(YandexNewsPageObject.WEBSITE)
@RequiredArgsConstructor
class YandexNewsPageObject implements CommonExtractorOperations {
    public static final String WEBSITE = "https://news.yandex.ru";
    private final WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector("#neo-page article a"));
        return links.stream().map(link -> link.getAttribute("href")).collect(toSet());
    }

    public Article extractInfoFromArticle(String articleUrl) {
        val article = new Article();
        driver.navigate().to(articleUrl);

        val titleElement = driver.findElement(By.cssSelector("#neo-page article a.mg-story__title-link"));
        article.setHeadline(titleElement.getText());

        val contentSnippets = driver.findElements(By.cssSelector(".mg-story__body .mg-snippets-group__item .mg-snippet__text span"));
        article.setContent(contentSnippets.stream().map(WebElement::getText).collect(joining("\n")));

        article.setArticleDate(now());
        return article;
    }
}
