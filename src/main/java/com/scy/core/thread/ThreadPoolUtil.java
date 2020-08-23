package com.scy.core.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * ThreadPoolUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/23.
 */
@Slf4j
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    public static boolean check(
            ExecutorService executorService,
            List<AbstractBooleanCallable> callables
    ) {
        CountDownLatch countDownLatch = new CountDownLatch(callables.size());
        List<BooleanFutureTask> booleanFutureTasks = callables.stream().map(callable -> new BooleanFutureTask(callable, countDownLatch)).collect(Collectors.toList());
        booleanFutureTasks.forEach(executorService::execute);

        try {
            countDownLatch.await();

            for (BooleanFutureTask task : booleanFutureTasks) {
                task.cancel(Boolean.TRUE);
            }

            for (BooleanFutureTask task : booleanFutureTasks) {
                if (!task.get()) {
                    return Boolean.FALSE;
                }
            }
        } catch (Exception e) {
            if (e instanceof CancellationException) {
            } else if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            } else {
                log.error("ThreadPoolUtil.check error", e);
            }
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
