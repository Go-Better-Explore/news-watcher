package ru.gobetter.newswatcher.extractor.core.api;

import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

public interface WebsiteArticlesExtractor {
    String getWebsite();

    List<Article> extractArticles();
}
