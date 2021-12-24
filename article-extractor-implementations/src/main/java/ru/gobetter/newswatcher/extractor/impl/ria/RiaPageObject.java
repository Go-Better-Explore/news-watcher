package ru.gobetter.newswatcher.extractor.impl.ria;

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
@Qualifier(RiaPageObject.WEBSITE)
class RiaPageObject implements CommonExtractorOperations {
    static final String WEBSITE = "https://ria.ru";
    @Setter
    private WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector("#content .section__content .cell-list__list .cell-list__item.m-no-image a.cell-list__item-link"));
        return getLinks(links);
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        val article = new Article();
        driver.navigate().to(articleUrl);

        val titleElement = driver.findElement(By.cssSelector("#content .article__title"));
        article.setHeadline(titleElement.getText());

        val contentBlocks = driver.findElements(By.cssSelector("#content .article__body .article__block"));
        article.setContent(getTexts(contentBlocks));

        article.setArticleDate(now());
        return article;
    }
}
