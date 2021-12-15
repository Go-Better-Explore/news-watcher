package ru.gobetter.newswatcher.extractor.impl.yandexnews;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
class YandexNewsExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "https://news.yandex.ru";
    private final YandexNewsPageObject yandex;

    @Override
    public List<Article> extractArticles() {
        val articleUrls = yandex.getArticlesUrls(getWebsite());
        log.debug("So, we got these article URLs", articleUrls);
        return articleUrls.stream()
            .map(yandex::extractInfoFromArticle)
            .peek(article -> log.debug(article.toString()))
            .collect(toList());
    }
}
