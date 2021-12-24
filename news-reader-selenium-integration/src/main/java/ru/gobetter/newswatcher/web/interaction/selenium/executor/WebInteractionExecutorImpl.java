package ru.gobetter.newswatcher.web.interaction.selenium.executor;

import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.BrowserWebDriverContainer;
import ru.gobetter.newswatcher.web.interaction.api.WebCommand;
import ru.gobetter.newswatcher.web.interaction.api.WebInteractionExecutor;
import ru.gobetter.newswatcher.web.interaction.selenium.pool.Pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Service
class WebInteractionExecutorImpl implements WebInteractionExecutor {
    private static final int CAPACITY = 2;
    private final ExecutorService executorService;
    private final Pool<BrowserWebDriverContainer> driverPool;

    WebInteractionExecutorImpl(Pool<BrowserWebDriverContainer> webDriverPool) {
        this.executorService = newFixedThreadPool(CAPACITY);
        this.driverPool = webDriverPool;
    }

    @Override
    public <R> CompletableFuture<R> execute(@NonNull WebCommand<R> command) {
        val task = new WebTask<>(driverPool, command);
        return CompletableFuture.supplyAsync(task::call, executorService);
    }
}
