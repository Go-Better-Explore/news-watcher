package ru.gobetter.newswatcher.analytics.logic;

import ru.gobetter.newswatcher.analytics.logic.model.WordsCount;
import ru.gobetter.newswatcher.model.entity.Article;

public interface WordsInArticleCounter {
    WordsCount scan(Article article);

    WordsCount scan(String text);
}
