package ru.gobetter.newswatcher.extractor.impl.ria;

import org.springframework.stereotype.Service;
import ru.gobetter.newswatcher.extractor.core.extractors.CommonExtractorOperations;
import ru.gobetter.newswatcher.model.entity.Article;

import java.util.Set;

@Service
class RiaNewsPageObject implements CommonExtractorOperations {
    @Override
    public Set<String> getArticlesUrls(String mainPageUrl) {
        return null;
    }

    @Override
    public Article extractInfoFromArticle(String articleUrl) {
        return null;
    }
}
