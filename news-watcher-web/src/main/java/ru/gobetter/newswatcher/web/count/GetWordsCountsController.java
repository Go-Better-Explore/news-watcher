package ru.gobetter.newswatcher.web.count;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gobetter.newswatcher.analytics.logic.wordscount.model.WordsCount;
import ru.gobetter.newswatcher.extractor.core.api.ArticlesExtractionService;
import ru.gobetter.newswatcher.model.entity.Article;
import ru.gobetter.newswatcher.model.entity.Website;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
class GetWordsCountsController {
    private final ArticlesExtractionService articlesExtractionService;

    @PostConstruct
    void afterInit() {
        log.debug("Well, I was added to the context!");
    }

    @GetMapping("/hello")
    String getGreeting() {
        return "hello, I'm news reader and I'm working!";
    }

    @GetMapping("/words")
    Map<Article, WordsCount> getWordsCount(@RequestBody WordsCountInputDto input) {
        return articlesExtractionService.extractInfoFrom(new Website(input.getWebsite()));
    }
}
