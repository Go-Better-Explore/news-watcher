package ru.gobetter.newswatcher.web.count;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

class WebsitesConstants {
    static final List<String> OPPOSITION_KEYS = listOf("lenta");
    static final List<String> PRO_RUSSIAN_KEYS = listOf("ria", "vesti");

    private static List<String> listOf(String... keys) {
        return unmodifiableList(asList(keys));
    }

    private WebsitesConstants() {
    }
}
