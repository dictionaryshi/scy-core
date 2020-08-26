package com.scy.core.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.scy.core.RuntimeUtil;
import com.scy.core.StringUtil;
import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
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

    public static final String THREAD_TASK_MESSAGE = "thread_task_message";

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
        } catch (Throwable e) {
            if (e instanceof CancellationException) {
            } else {
                log.error(MessageUtil.format("ThreadPoolUtil.check error", e));
            }
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static ThreadPoolExecutor getThreadPool(
            String poolName,
            int corePoolSize,
            int maximumPoolSize,
            int queueSize
    ) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + "-thread-pool-%d")
                .setUncaughtExceptionHandler((thread, throwable) -> log.error(MessageUtil.format("UncaughtExceptionHandler error " + getThreadTaskMessage(), throwable)))
                .build();

        TransmittableThreadPoolExecutor transmittableThreadPoolExecutor = new TransmittableThreadPoolExecutor(
                corePoolSize, maximumPoolSize, 300, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), threadFactory,
                (runnable, threadPoolExecutor) -> log.error(MessageUtil.format("thread pool reject " + getThreadTaskMessage()))
        );

        RuntimeUtil.addShutdownHook(new Thread(() -> {
            try {
                shutdown(transmittableThreadPoolExecutor, poolName);
            } catch (Throwable e) {
                log.error(MessageUtil.format("thread pool shutdown error", e, "poolName", poolName));
            }
        }));

        return transmittableThreadPoolExecutor;
    }

    public static void shutdown(ExecutorService executorService, String poolName) throws Throwable {
        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            log.warn(MessageUtil.format("thread pool shutdown awaitTermination", "poolName", poolName));
        }
        executorService.shutdownNow();
    }

    public static void setThreadTaskMessage(String message) {
        ThreadLocalUtil.put(THREAD_TASK_MESSAGE, message);
    }

    public static String getThreadTaskMessage() {
        String message = (String) ThreadLocalUtil.get(THREAD_TASK_MESSAGE);
        if (StringUtil.isEmpty(message)) {
            return StringUtil.EMPTY;
        }
        return message;
    }
}
