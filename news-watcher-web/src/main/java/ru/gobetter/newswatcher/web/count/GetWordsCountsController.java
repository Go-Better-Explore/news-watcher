package ru.gobetter.newswatcher.web.count;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.extractor.core.api.ArticlesExtractionService;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount.sum;
import static ru.gobetter.newswatcher.web.count.WebsitesConstants.OPPOSITION_KEYS;
import static ru.gobetter.newswatcher.web.count.WebsitesConstants.PRO_RUSSIAN_KEYS;

@RestController
@RequiredArgsConstructor
@Slf4j
class GetWordsCountsController {
    private final ArticlesExtractionService articlesExtractionService;

    @GetMapping("/words")
    Map<String, Integer> getWordsCount(@RequestBody(required = false) WordsCountInputDto input) {
        val extraction = ofNullable(input)
            .map(WordsCountInputDto::getWebsite)
            .filter(not(String::isBlank))
            .map(Website::new)
            .map(articlesExtractionService::extractFrom)
            .orElseGet(articlesExtractionService::extractAll);
        val result = sum(extraction.values());
        return result.getOrderedCountData();
    }

    @GetMapping("/words/opposition")
    Map<String, Integer> getOppositionWords() {
        val counts = getFrom(OPPOSITION_KEYS);
        return counts.getOrderedCountData();
    }

    @GetMapping("/words/prorussian")
    Map<String, Integer> getProRussianWords() {
        val counts = getFrom(PRO_RUSSIAN_KEYS);
        return counts.getOrderedCountData();
    }

    private WordsCount getFrom(List<String> keys) {
        val websites = keys.stream().map(Website::new).collect(toList());
        val result = articlesExtractionService.extractFrom(websites);
        return sum(result.values());
    }
}
