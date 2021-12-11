package ru.gobetter.newswatcher.extractor.core.extractors;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
class ArticleExtractorFactoryImpl implements ArticleExtractorFactory {
    private final Map<String, WebsiteArticlesExtractor> extractorMap;

    ArticleExtractorFactoryImpl(@NonNull List<WebsiteArticlesExtractor> extractors) {
        this.extractorMap = extractors.stream()
            .collect(Collectors.toUnmodifiableMap(WebsiteArticlesExtractor::getWebsite, identity()));
    }

    @Override
    public WebsiteArticlesExtractor getExtractorFor(Website website) {
        return extractorMap.get(website.getUrl());
    }
}
