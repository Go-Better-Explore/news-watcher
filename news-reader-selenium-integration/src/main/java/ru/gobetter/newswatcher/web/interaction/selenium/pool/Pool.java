package ru.gobetter.newswatcher.web.interaction.selenium.pool;

public interface Pool<T> {
    ObjectHandle<T> obtain();
}
