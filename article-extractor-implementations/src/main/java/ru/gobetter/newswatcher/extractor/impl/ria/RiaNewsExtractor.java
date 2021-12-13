package ru.gobetter.newswatcher.extractor.impl.ria;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
class RiaNewsExtractor implements WebsiteArticlesExtractor {
    private final RiaNewsPageObject ria;

    @Getter
    private final String website = "http://ria.ru";

    @Override
    public List<Article> extractArticles() {
        val articleUrls = ria.getArticlesUrls(getWebsite());
        log.debug("So, we got these article URLs", articleUrls);
        return articleUrls.stream()
            .map(ria::extractInfoFromArticle)
            .peek(article -> log.debug(article.toString()))
            .collect(toList());
    }
}
