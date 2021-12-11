package ru.gobetter.newswatcher.extractor.core;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
class AtriclesExtractionServiceImpl implements ArticlesExtractionService {
    private final WordsInArticleCounter counter;
    private final ArticleExtractorFactory aeFactory;

    @Override
    public Map<Article, WordsCount> extractInfoFrom(Website website) {
        WebsiteArticlesExtractor extractor = aeFactory.getExtractorFor(website);
        val articles = extractor.extractArticles(website);
        val result = new HashMap<Article, WordsCount>();
        articles.forEach(article -> result.put(article, counter.scan(article)));
        return result;
    }
}
