package ru.gobetter.newswatcher.extractor.core.api;

import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.Collection;
import java.util.Map;

public interface ArticlesExtractionService {
    Map<Article, WordsCount> extractAll();

    Map<Article, WordsCount> extractFrom(Collection<Website> websites);

    Map<Article, WordsCount> extractFrom(Website website);
}
