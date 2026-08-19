package org.example.stub.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class DelayManager {
    private static final long MIN_DELAY = 1000;
    private static final long MAX_DELAY = 2001;

    public static CompletableFuture<Void> delayAsync() {

        long delay = ThreadLocalRandom.current()
                .nextLong(MIN_DELAY, MAX_DELAY);

        return CompletableFuture.runAsync(
                () -> {},
                CompletableFuture.delayedExecutor(
                        delay,
                        TimeUnit.MILLISECONDS
                )
        );
    }

}
