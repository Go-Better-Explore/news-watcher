package ru.gobetter.newswatcher.extractor.impl.yandexnews;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
class YandexNewsExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "news.yandex.ru";
    private final YandexNewsPageObject yandex;

    @Override
    public List<Article> extractArticles(Website website) {
        val articleUrls = yandex.getArticleUrls(this.website);
        return articleUrls.stream().map(yandex::extractInfoFromArticle).collect(toList());
    }
}
