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
class RiaExtractor implements WebsiteArticlesExtractor {
    private final RiaPageObject ria;

    @Getter
    private final String website = "https://ria.ru";

    @Override
    public List<Article> extractArticles() {
        val articleUrls = ria.getArticlesUrls(getWebsite());
        return articleUrls.stream()
            .map(ria::extractInfoFromArticle)
            .collect(toList());
    }
}
