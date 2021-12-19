package ru.gobetter.newswatcher.analytics.logic.wordscount.model;

import lombok.Data;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

@Data
public class WordsCount {
    private static final Comparator<Map.Entry<String, AtomicInteger>> BY_WORDS_COMPARATOR;

    private final Map<String, AtomicInteger> wordsCount = new HashMap<>();

    static {
        Comparator<Map.Entry<String, AtomicInteger>> comparator = comparingInt(e -> e.getValue().get());
        BY_WORDS_COMPARATOR = comparator.reversed();
    }

    public Map<String, Integer> getOrderedCountData() {
        val entryList = new ArrayList<>(wordsCount.entrySet());
        entryList.sort(BY_WORDS_COMPARATOR);

        val result = new LinkedHashMap<String, Integer>();
        entryList.forEach(entry -> result.put(entry.getKey(), entry.getValue().get()));
        return result;
    }

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

    public final WordsCount getTop(int n) {
        val topWords = wordsCount.entrySet().stream()
            .sorted(BY_WORDS_COMPARATOR)
            .limit(n)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        val result = new WordsCount();
        result.wordsCount.putAll(topWords);
        return result;
    }

    public static WordsCount sum(Collection<WordsCount> counts) {
        WordsCount result = null;
        for (WordsCount count : counts) {
            if (result == null) {
                result = count;
                continue;
            }
            result = result.add(count);
        }
        return result;
    }
}
