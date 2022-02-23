package com.scy.core.thread;

import com.google.common.collect.Maps;
import com.scy.core.ObjectUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.trace.TraceUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
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
    public void execute(Runnable runnable) {
        Map<String, Object> threadLocalMap = Maps.newHashMap(ThreadLocalUtil.getThreadLocalMap());
        super.execute(() -> {
            ThreadLocalUtil.putAll(threadLocalMap);
            TraceUtil.setMdcTraceId();

            log.info(MessageUtil.format("thread start", "thread", Thread.currentThread().getName()));
            runnable.run();
        });
    }

    @Override
    public void afterExecute(Runnable runnable, Throwable throwable) {
        if (ObjectUtil.isNull(throwable)) {
            log.info(MessageUtil.format("thread end", "thread", Thread.currentThread().getName()));
        } else {
            log.error(MessageUtil.format("thread error", throwable, "thread", Thread.currentThread().getName()));
        }
        TraceUtil.clearTrace();
    }
}
