package ru.gobetter.newswatcher.extractor.impl.echo;

import lombok.Setter;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static ru.gobetter.newswatcher.extractor.impl.utils.SeleniumHelper.getLinks;
import static ru.gobetter.newswatcher.extractor.impl.utils.SeleniumHelper.getTexts;

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
        val newsElement = driver.findElements(By.cssSelector(".menubar a.menulink span"))
            .stream()
            .filter(element -> "Новости".equals(element.getText()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Element not found"));
        newsElement.click();
        val newsLinks = driver.findElements(By.cssSelector(".newsblock h3 a"));
        return getLinks(newsLinks);
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        driver.navigate().to(articleUrl);
        val article = new Article();

        val titleElement = driver.findElement(By.cssSelector(".conthead h1"));
        article.setHeadline(titleElement.getText());

        val contentElements = driver.findElements(By.cssSelector(".typical p"));
        article.setContent(getTexts(contentElements));

        article.setArticleDate(now());

        return article;
    }
}
