package ru.gobetter.newswatcher.analytics.logic.wordscount;

import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.model.entity.Article;

public interface WordsInArticleCounter {
    WordsCount scan(Article article);

    WordsCount scan(String text);
}
