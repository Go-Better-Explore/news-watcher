package ru.gobetter.newswatcher.extractor.impl.rt;

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
@Qualifier(RTPageObject.WEBSITE)
class RTPageObject implements CommonExtractorOperations {
    static final String WEBSITE = "https://russian.rt.com";

    @Setter
    private WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector(".layout__content .card__heading a.link"));
        return getLinks(links);
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        driver.navigate().to(articleUrl);
        val article = new Article();

        val headingElement = driver.findElement(By.cssSelector("h1.article__heading"));
        article.setHeadline(headingElement.getText());

        val contentParagraphs = driver.findElements(By.cssSelector(".article .article__summary"));
        contentParagraphs.addAll(driver.findElements(By.cssSelector(".article .article__text p")));
        article.setContent(getTexts(contentParagraphs));

        article.setArticleDate(now());
        return article;
    }
}
