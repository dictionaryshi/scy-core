package com.scy.core.thread;

import com.scy.core.format.MessageUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * BooleanFutureTask
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/23.
 */
@Slf4j
@Getter
public class BooleanFutureTask extends FutureTask<Boolean> {

    private final String taskName;

    private final CountDownLatch countDownLatch;

    public BooleanFutureTask(AbstractBooleanCallable callable, CountDownLatch countDownLatch) {
        super(callable);
        this.taskName = callable.getTaskName();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void done() {
        try {
            if (!super.get()) {
                countDown();
                log.info(MessageUtil.format("BooleanFutureTask fail", "taskName", taskName, "thread", Thread.currentThread().getName()));
            } else {
                log.info(MessageUtil.format("BooleanFutureTask success", "taskName", taskName, "thread", Thread.currentThread().getName()));
            }
        } catch (Throwable e) {
            countDown();
            if (e instanceof CancellationException) {
                log.warn(MessageUtil.format("BooleanFutureTask cancel", "taskName", taskName, "thread", Thread.currentThread().getName()));
            } else {
                log.error(MessageUtil.format("BooleanFutureTask error", e, "taskName", taskName, "thread", Thread.currentThread().getName()));
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    private void countDown() {
        long count = countDownLatch.getCount();
        for (int i = 0; i < count; i++) {
            countDownLatch.countDown();
        }
    }
}
