package ru.gobetter.newswatcher.web.interaction.selenium.pool;

public interface ObjectHandle<T> {
    T get();

    void release();
}
