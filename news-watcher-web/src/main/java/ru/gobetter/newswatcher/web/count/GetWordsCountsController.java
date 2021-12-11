package ru.gobetter.newswatcher.web.count;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.extractor.core.api.ArticlesExtractionService;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import java.util.Map;

@RestController
@RequiredArgsConstructor
class GetWordsCountsController {
    private final ArticlesExtractionService articlesExtractionService;

    @GetMapping("/words")
    Map<Article, WordsCount> getWordsCount(@RequestBody WordsCountInputDto input) {
        return articlesExtractionService.extractInfoFrom(new Website(input.getWebsite()));
    }
}
