package ru.gobetter.newswatcher.extractor.core.extractors;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.Collection;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

@Service
class ArticleExtractorFactoryImpl implements ArticleExtractorFactory {
    private final Map<String, CommonExtractorOperations> extractorMap;

    ArticleExtractorFactoryImpl(ListableBeanFactory factory) {
        this.extractorMap = unmodifiableMap(factory.getBeansOfType(CommonExtractorOperations.class));
    }

    @Override
    public Collection<WebsiteArticlesExtractor> getAllExtractors() {
        return extractorMap.values().stream()
            .map(CommonExtractor::new)
            .collect(toList());
    }

    @Override
    public WebsiteArticlesExtractor getExtractorFor(Website website) {
        return extractorMap.keySet().stream()
            .filter(url -> url.contains(website.getUrl()))
            .findFirst()
            .map(extractorMap::get)
            .map(CommonExtractor::new)
            .orElse(null);
    }
}
