package ru.gobetter.newswatcher.extractor.impl.vesti;

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
import static ru.gobetter.newswatcher.extractor.impl.utils.SeleniumHelper.getLinks;

@Service
@Qualifier(VestiPageObject.WEBSITE)
class VestiPageObject implements CommonExtractorOperations {
    public static final String WEBSITE = "https://vesti.ru";
    @Setter
    private WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector(".main-news__title a"));
        return getLinks(links);
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        driver.navigate().to(articleUrl);
        val article = new Article();

        val headline = driver.findElement(By.cssSelector(".article__title")).getText();
        article.setHeadline(headline);

        val content = driver.findElements(By.cssSelector(".article__text p"))
            .stream()
            .map(WebElement::getText)
            .collect(joining("\n"));
        article.setContent(content);

        return article;
    }
}
