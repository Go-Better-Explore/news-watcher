package ru.gobetter.newswatcher.extractor.core.extractors;

import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

public interface CommonExtractorOperations {
    Set<String> getArticlesUrls(String mainPageUrl);

    Article extractInfoFromArticle(String articleUrl);
}
