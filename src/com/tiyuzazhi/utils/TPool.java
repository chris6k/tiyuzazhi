package com.tiyuzazhi.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author chris.xue
 */
public class TPool {
    private static final ExecutorService exec = Executors.newScheduledThreadPool(1);

    public static void post(Runnable runnable) {
        exec.submit(runnable);
    }

    public static <V> Future<V> submit(Callable<V> callable) {
        return exec.submit(callable);
    }
}
