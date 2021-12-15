package ru.gobetter.newswatcher.extractor.impl.vesti;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.api.WebsiteArticlesExtractor;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
class VestiExtractor implements WebsiteArticlesExtractor {
    @Getter
    private final String website = "https://www.vesti.ru/";
    private final VestiPageObject vesti;

    @Override
    public List<Article> extractArticles() {
        val articleUrls = vesti.getArticlesUrls(getWebsite());
        return articleUrls.stream()
            .map(vesti::extractInfoFromArticle)
            .collect(toList());
    }
}
