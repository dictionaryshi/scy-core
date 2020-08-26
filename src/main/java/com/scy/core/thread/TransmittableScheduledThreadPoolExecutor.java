package com.scy.core.thread;

import com.alibaba.ttl.TtlRunnable;
import com.scy.core.UUIDUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.trace.TraceUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * TransmittableScheduledThreadPoolExecutor
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/26.
 */
@Slf4j
public class TransmittableScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

    public TransmittableScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    @Override
    public void beforeExecute(Thread thread, Runnable runnable) {
        TraceUtil.setTraceId(UUIDUtil.uuid());
        log.info(MessageUtil.format("thread start", "thread", Thread.currentThread().getName()));
    }

    @Override
    public void execute(Runnable runnable) {
        super.execute(TtlRunnable.get(runnable));
    }

    @Override
    public void afterExecute(Runnable runnable, Throwable throwable) {
        log.info(MessageUtil.format("thread end", "thread", Thread.currentThread().getName()));
        TraceUtil.clearMdc();
    }
}
