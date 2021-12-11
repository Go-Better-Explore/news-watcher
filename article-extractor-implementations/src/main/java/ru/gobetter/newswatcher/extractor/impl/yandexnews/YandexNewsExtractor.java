package ru.gobetter.newswatcher.extractor.impl.yandexnews;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.List;

@Service
class YandexNewsExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "news.yandex.ru";

    @Override
    public List<Article> extractArticles(Website website) {
        return null;
    }
}
