package com.scy.core.thread;

import com.alibaba.ttl.TtlRunnable;
import com.scy.core.trace.TraceUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * TransmittableThreadPoolExecutor
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/25.
 */
@Slf4j
public class TransmittableThreadPoolExecutor extends ThreadPoolExecutor {

    public TransmittableThreadPoolExecutor(
            int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler
    ) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void beforeExecute(Thread thread, Runnable runnable) {
        TraceUtil.setMdcTraceId();
    }

    @Override
    public void execute(Runnable runnable) {
        super.execute(TtlRunnable.get(runnable));
    }

    public void execute(String message, Runnable runnable) {
        ThreadPoolUtil.setThreadTaskMessage(message);
        this.execute(runnable);
    }

    public <T> Future<T> submit(String message, Callable<T> callable) {
        ThreadPoolUtil.setThreadTaskMessage(message);
        return super.submit(callable);
    }

    @Override
    public void afterExecute(Runnable runnable, Throwable throwable) {
        TraceUtil.clearMdc();
    }
}
