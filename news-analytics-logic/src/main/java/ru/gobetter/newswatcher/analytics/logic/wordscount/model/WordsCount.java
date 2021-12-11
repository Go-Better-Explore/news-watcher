package ru.gobetter.newswatcher.analytics.logic.wordscount.model;

import lombok.Data;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class WordsCount {
    private final Map<String, AtomicInteger> wordsCount = new HashMap<>();

    public final void incWord(String word) {
        wordsCount.computeIfAbsent(word, (w) -> new AtomicInteger())
            .incrementAndGet();
    }

    public final WordsCount add(WordsCount another) {
        val newCount = new WordsCount();
        newCount.wordsCount.putAll(this.wordsCount);
        another.wordsCount.forEach((word, count) -> newCount.wordsCount.computeIfAbsent(word, (k) -> new AtomicInteger())
            .addAndGet(count.get()));
        return newCount;
    }
}
