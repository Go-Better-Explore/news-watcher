package ru.gobetter.newswatcher.extractor.impl.utils;

import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class SeleniumHelper {
    private SeleniumHelper() {
    }

    public static Set<String> getLinks(Collection<WebElement> links) {
        return links.stream()
            .map(link -> link.getAttribute("href"))
            .collect(toSet());
    }

    public static String getTexts(Collection<WebElement> paragraphs) {
        return paragraphs.stream()
            .map(WebElement::getText)
            .collect(joining("\n"));
    }
}
