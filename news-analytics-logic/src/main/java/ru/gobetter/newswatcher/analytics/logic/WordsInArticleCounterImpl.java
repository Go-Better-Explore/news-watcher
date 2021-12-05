package ru.gobetter.newswatcher.analytics.logic;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.analytics.logic.model.WordsCount;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Arrays;

@Service
class WordsInArticleCounterImpl implements WordsInArticleCounter {
    @Override
    public WordsCount scan(Article article) {
        return scan(article.getHeadline())
            .add(scan(article.getContent()));
    }

    @Override
    public WordsCount scan(String text) {
        val trimmedText = text.replaceAll("[^a-zA-Zа-яА-Я]", " ");
        val words = trimmedText.split("\\s+");
        val wordsCount = new WordsCount();
        Arrays.stream(words).forEach(wordsCount::incWord);
        return wordsCount;
    }
}
