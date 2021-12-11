package ru.gobetter.newswatcher.extractor.core.extractors;

import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Website;

public interface ArticleExtractorFactory {
    WebsiteArticlesExtractor getExtractorFor(Website website);
}
