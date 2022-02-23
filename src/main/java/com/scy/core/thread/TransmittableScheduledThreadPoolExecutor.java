package com.scy.core.thread;

import com.scy.core.ObjectUtil;
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
    public void execute(Runnable command) {
        super.execute(command);
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
