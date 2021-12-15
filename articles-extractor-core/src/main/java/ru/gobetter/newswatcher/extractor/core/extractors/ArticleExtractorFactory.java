package ru.gobetter.newswatcher.extractor.core.extractors;

import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.Collection;

public interface ArticleExtractorFactory {
    Collection<WebsiteArticlesExtractor> getAllExtractors();

    WebsiteArticlesExtractor getExtractorFor(Website website);
}
