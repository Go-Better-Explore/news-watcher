package ru.gobetter.newswatcher.extractor.impl.vesti;

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

@Slf4j
@Service
@RequiredArgsConstructor
class VestiExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "https://www.vesti.ru/";
    private final VestiPageObject vesti;

    @Override
    public List<Article> extractArticles() {
        val articleUrls = vesti.getArticlesUrls(getWebsite());
        return articleUrls.stream()
            .peek(url -> log.info("Going to " + url))
            .map(url -> {
                try {
                    return vesti.extractInfoFromArticle(url);
                } catch (Exception e) {
                    log.warn("Failed to extract data from " + url, e);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(toList());
    }
}
