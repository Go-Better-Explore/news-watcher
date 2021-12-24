package ru.gobetter.newswatcher.extractor.impl.echo;

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

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.joining;
import static ru.gobetter.newswatcher.extractor.impl.utils.SeleniumHelper.getLinks;

@Service
@Qualifier(EchoPageObject.WEBSITE)
class EchoPageObject implements CommonExtractorOperations {
    static final String WEBSITE = "https://echo.msk.ru";

    @Setter
    private WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val newsElement = driver.findElement(By.xpath("//*[text()='Новости']"));
        newsElement.click();
        val newsLinks = driver.findElements(By.cssSelector(".newsblock h3 a"));
        return getLinks(newsLinks);
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        driver.navigate().to(articleUrl);
        val article = new Article();

        val titleElement = driver.findElement(By.cssSelector("h1[itemprop='headline']"));
        article.setHeadline(titleElement.getText());

        val contentElements = driver.findElements(By.cssSelector("div[itemprop='articleBody'] p"));
        article.setContent(contentElements.stream().map(WebElement::getText).collect(joining("\n")));

        article.setArticleDate(now());

        return article;
    }
}
