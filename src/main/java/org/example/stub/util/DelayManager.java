package org.example.stub.util;

import java.util.concurrent.ThreadLocalRandom;

public class DelayManager {
    private static final long MIN_DELAY = 1000;
    private static final long MAX_DELAY = 2001;

    public static void delay() {
        try {
            long delay = ThreadLocalRandom.current().nextLong(MIN_DELAY,MAX_DELAY);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
