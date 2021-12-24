package ru.gobetter.newswatcher.web.interaction.api;

import java.util.concurrent.CompletableFuture;

public interface WebInteractionExecutor {
    <R> CompletableFuture<R> execute(WebCommand<R> command);
}
