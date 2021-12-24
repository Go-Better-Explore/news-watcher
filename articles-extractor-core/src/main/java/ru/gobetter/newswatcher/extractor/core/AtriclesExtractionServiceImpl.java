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
import ru.gobetter.newswatcher.extractor.core.extractors.ExtractorWebCommand;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;
import ru.gobetter.newswatcher.web.interaction.api.WebInteractionExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
class AtriclesExtractionServiceImpl implements ArticlesExtractionService {
    private final WordsInArticleCounter counter;
    private final WebInteractionExecutor webInteractionExecutor;
    private final ArticleExtractorFactory aeFactory;

    @Override
    public Map<Article, WordsCount> extractAll() {
        val extractors = aeFactory.getAllExtractors();
        val articles = execute(new ArrayList<>(extractors));
        return extract(articles);
    }

    @Override
    public Map<Article, WordsCount> extractFrom(Collection<Website> websites) {
        val extractors = websites.stream().map(aeFactory::getExtractorFor).collect(toList());
        val articles = execute(extractors);
        return extract(articles);
    }

    @Override
    public Map<Article, WordsCount> extractFrom(Website website) {
        val extractor = aeFactory.getExtractorFor(website);
        val article = execute(extractor);
        return extract(singletonList(article));
    }

    private List<CompletableFuture<List<Article>>> execute(List<WebsiteArticlesExtractor> extractors) {
        return extractors.stream().map(this::execute).collect(toList());
    }

    private CompletableFuture<List<Article>> execute(WebsiteArticlesExtractor extractor) {
        return webInteractionExecutor.execute(new ExtractorWebCommand(extractor));
    }

    private Map<Article, WordsCount> extract(List<CompletableFuture<List<Article>>> articleFutures) {
        return articleFutures.stream()
            .map(future -> future.thenApply(articles -> {
                val count = new HashMap<Article, WordsCount>();
                articles.forEach(article -> count.put(article, counter.scan(article)));
                return count;
            }))
            .map(CompletableFuture::join)
            .filter(Objects::nonNull)
            .flatMap(wordsCount -> wordsCount.entrySet().stream())
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, WordsCount::add));
    }
}
