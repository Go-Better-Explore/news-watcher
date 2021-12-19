package ru.gobetter.newswatcher.extractor.core.api;

import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

public interface WebsiteArticlesExtractor {
    List<Article> extractArticles();
}
