package ru.gobetter.newswatcher.extractor.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.analytics.logic.wordscount.WordsInArticleCounter;
import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.extractor.core.api.ArticlesExtractionService;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.extractor.core.extractors.ArticleExtractorFactory;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
class AtriclesExtractionServiceImpl implements ArticlesExtractionService {
    private final WordsInArticleCounter counter;
    private final ArticleExtractorFactory aeFactory;

    @Override
    public Map<Article, WordsCount> extractAll() {
        val extractors = aeFactory.getAllExtractors();
        return extractors.stream()
            .map(extractor -> {
                try {
                    return extractWith(extractor);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .flatMap(wordsCount -> wordsCount.entrySet().stream())
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, WordsCount::add));
    }

    @Override
    public Map<Article, WordsCount> extractInfoFrom(Website website) {
        WebsiteArticlesExtractor extractor = aeFactory.getExtractorFor(website);
        return extractWith(extractor);
    }

    private Map<Article, WordsCount> extractWith(WebsiteArticlesExtractor extractor) {
        val articles = extractor.extractArticles();
        log.info(articles.toString());
        log.info("Articles retrieval is done!");
        val result = new HashMap<Article, WordsCount>();
        articles.forEach(article -> result.put(article, counter.scan(article)));
        return result;
    }
}
