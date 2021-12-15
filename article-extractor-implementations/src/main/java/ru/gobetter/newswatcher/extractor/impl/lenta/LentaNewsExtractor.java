package ru.gobetter.newswatcher.extractor.impl.lenta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class LentaNewsExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "https://lenta.ru/";
    private final LentaPageObject lenta;

    @Override
    public List<Article> extractArticles() {
        val articleUrls = lenta.getArticlesUrls(getWebsite());
        return articleUrls.stream()
            .map(articleUrl -> {
                try {
                    return lenta.extractInfoFromArticle(articleUrl);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(toList());
    }
}
