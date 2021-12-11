package ru.gobetter.newswatcher.extractor.core.api;

import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.List;

public interface WebsiteArticlesExtractor {
    String getWebsite();

    List<Article> extractArticles(Website website);
}
