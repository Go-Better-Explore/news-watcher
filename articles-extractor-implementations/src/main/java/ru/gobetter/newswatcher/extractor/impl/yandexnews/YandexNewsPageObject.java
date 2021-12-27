package ru.gobetter.newswatcher.extractor.impl.yandexnews;

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
@Qualifier(YandexNewsPageObject.WEBSITE)
class YandexNewsPageObject implements CommonExtractorOperations {
    static final String WEBSITE = "https://news.yandex.ru";
    @Setter
    private WebDriver driver;

    @Override
    public String getWebsite() {
        return WEBSITE;
    }

    public Set<String> getArticlesUrls(String mainPageUrl) {
        driver.navigate().to(mainPageUrl);
        val links = driver.findElements(By.cssSelector("#neo-page article a"));
        return getLinks(links);
    }

    public Article extractInfoFromArticle(String articleUrl) {
        val article = new Article();
        driver.navigate().to(articleUrl);

        val titleElement = driver.findElement(By.cssSelector("#neo-page article a.mg-story__title-link"));
        article.setHeadline(titleElement.getText());

        val contentSnippets = driver.findElements(By.cssSelector(".mg-story__body .mg-snippets-group__item .mg-snippet__text span"));
        article.setContent(getTexts(contentSnippets));

        article.setArticleDate(now());
        return article;
    }
}
