package com.scy.core.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.scy.core.ObjectUtil;
import com.scy.core.exception.BusinessException;
import com.scy.core.format.MessageUtil;
import com.scy.core.model.ThreadMonitorBO;
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

    public static final ConcurrentHashMap<String, ThreadPoolExecutor> THREAD_POOLS = new ConcurrentHashMap<>();

    public static final String FORK_JOIN_POOL_CORE_SIZE = "java.util.concurrent.ForkJoinPool.common.parallelism";

    public static boolean parallelCheck(
            ThreadPoolExecutor threadPoolExecutor,
            List<AbstractBooleanCallable> callables
    ) {
        CountDownLatch countDownLatch = new CountDownLatch(callables.size());
        List<BooleanFutureTask> booleanFutureTasks = callables.stream().map(callable -> new BooleanFutureTask(callable, countDownLatch)).collect(Collectors.toList());
        booleanFutureTasks.forEach(threadPoolExecutor::execute);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(MessageUtil.format("parallelCheck await interrupted", "thread", Thread.currentThread().getName()));
        }

        for (BooleanFutureTask task : booleanFutureTasks) {
            task.cancel(Boolean.TRUE);
        }

        for (BooleanFutureTask task : booleanFutureTasks) {
            try {
                if (!task.get()) {
                    log.info(MessageUtil.format("parallelCheck fail", "taskName", task.getTaskName(), "thread", Thread.currentThread().getName()));
                    return Boolean.FALSE;
                }
            } catch (Throwable e) {
                if (e instanceof CancellationException) {
                    log.warn(MessageUtil.format("parallelCheck cancel", "taskName", task.getTaskName(), "thread", Thread.currentThread().getName()));
                } else {
                    log.error(MessageUtil.format("parallelCheck error", e, "taskName", task.getTaskName(), "thread", Thread.currentThread().getName()));
                }
                return Boolean.FALSE;
            }
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
                .build();

        TransmittableThreadPoolExecutor transmittableThreadPoolExecutor = new TransmittableThreadPoolExecutor(
                corePoolSize, maximumPoolSize, 300, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize), threadFactory,
                (runnable, threadPoolExecutor) -> {
                    throw new BusinessException(MessageUtil.format("thread pool reject", "poolName", poolName, "threadMonitor", getMonitorInfo(threadPoolExecutor)));
                }
        );

        if (THREAD_POOLS.containsKey(poolName)) {
            log.error(MessageUtil.format("线程池重复定义", "poolName", poolName));
            return null;
        }

        THREAD_POOLS.putIfAbsent(poolName, transmittableThreadPoolExecutor);

        return transmittableThreadPoolExecutor;
    }

    public static void shutdown(ThreadPoolExecutor threadPoolExecutor, String poolName) throws Throwable {
        if (ObjectUtil.isNull(threadPoolExecutor)) {
            return;
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            log.warn(MessageUtil.format("thread pool shutdown awaitTermination",
                    "poolName", poolName, "thread", Thread.currentThread().getName(), "threadMonitor", getMonitorInfo(threadPoolExecutor)));
        }
        log.info(MessageUtil.format("thread pool shutdown awaitTermination finish",
                "poolName", poolName, "thread", Thread.currentThread().getName(), "threadMonitor", getMonitorInfo(threadPoolExecutor)));
        threadPoolExecutor.shutdownNow();
    }

    public static ThreadMonitorBO getMonitorInfo(ThreadPoolExecutor threadPoolExecutor) {
        ThreadMonitorBO threadMonitorBO = new ThreadMonitorBO();
        threadMonitorBO.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        threadMonitorBO.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        threadMonitorBO.setWorkQueueSize(threadPoolExecutor.getQueue().size());
        threadMonitorBO.setPoolSize(threadPoolExecutor.getPoolSize());
        threadMonitorBO.setActiveCount(threadPoolExecutor.getActiveCount());
        threadMonitorBO.setLargestPoolSize(threadPoolExecutor.getLargestPoolSize());
        threadMonitorBO.setTaskCount(threadPoolExecutor.getTaskCount());
        threadMonitorBO.setCompletedTaskCount(threadPoolExecutor.getCompletedTaskCount());
        return threadMonitorBO;
    }

    public static ScheduledThreadPoolExecutor getScheduledPool(
            String poolName,
            int corePoolSize
    ) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + "-scheduled-thread-pool-%d")
                .build();
        TransmittableScheduledThreadPoolExecutor transmittableScheduledThreadPoolExecutor = new TransmittableScheduledThreadPoolExecutor(
                corePoolSize,
                threadFactory,
                (runnable, threadPoolExecutor) -> log.error(MessageUtil.format("thread pool reject",
                        "poolName", poolName, "thread", Thread.currentThread().getName(), "threadMonitor", getMonitorInfo(threadPoolExecutor)))
        );

        if (THREAD_POOLS.containsKey(poolName)) {
            log.error(MessageUtil.format("线程池重复定义", "poolName", poolName));
            return null;
        }

        THREAD_POOLS.putIfAbsent(poolName, transmittableScheduledThreadPoolExecutor);

        return transmittableScheduledThreadPoolExecutor;
    }

    public static void setForkJoinPoolCoreSize(int coreSize) {
        // 主线程也会参与执行
        System.setProperty(FORK_JOIN_POOL_CORE_SIZE, String.valueOf(coreSize - 1));
    }
}
