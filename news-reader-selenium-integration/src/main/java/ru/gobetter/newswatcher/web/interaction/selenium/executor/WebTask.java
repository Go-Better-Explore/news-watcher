package ru.gobetter.newswatcher.web.interaction.selenium.executor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.testcontainers.containers.BrowserWebDriverContainer;
import ru.gobetter.newswatcher.web.interaction.api.WebCommand;
import ru.gobetter.newswatcher.web.interaction.selenium.pool.Pool;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
final class WebTask<R> implements Callable<R> {
    private final Pool<BrowserWebDriverContainer> driverPool;
    private final WebCommand<R> command;

    @Override
    public R call() {
        val driverHandle = driverPool.obtain();
        R result;
        try {
            val driver = driverHandle.get().getWebDriver();
//            driver.getKeyboard().sendKeys(Keys.CONTROL, "T");
            result = command.execute(driver);
//            driver.close();
        } finally {
            driverHandle.release();
        }
        return result;
    }
}
