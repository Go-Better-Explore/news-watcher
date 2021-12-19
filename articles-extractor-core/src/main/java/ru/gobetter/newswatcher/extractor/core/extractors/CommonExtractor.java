package ru.gobetter.newswatcher.extractor.core.extractors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
class CommonExtractor implements WebsiteArticlesExtractor {
    private final CommonExtractorOperations pageObject;

    @Override
    public List<Article> extractArticles() {
        val articleUrls = pageObject.getArticlesUrls(pageObject.getWebsite());
        return articleUrls.stream().map(articleUrl -> {
            try {
                return pageObject.extractInfoFromArticle(articleUrl);
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(toList());
    }
}
