package ru.gobetter.newswatcher.extractor.impl.lenta;

import lombok.Setter;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Service
@Qualifier(LentaPageObject.WEBSITE)
public class LentaPageObject implements CommonExtractorOperations {
    public static final String WEBSITE = "https://lenta.ru";
    @Setter
    private WebDriver driver;

    public String getWebsite() {
        return WEBSITE;
    }

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
        driver.navigate().to(articleUrl);

        val article = new Article();

        val titleElement = driver.findElement(By.cssSelector(".topic-header__title"));
        article.setHeadline(titleElement.getText());

        val contentElements = driver.findElements(By.cssSelector(".topic-body__content .topic-body__content-text"));
        article.setContent(contentElements.stream().map(WebElement::getText).collect(joining("\n")));

        return article;
    }
}
