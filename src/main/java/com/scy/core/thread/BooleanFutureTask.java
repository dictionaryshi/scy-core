package com.scy.core.thread;

import com.scy.core.format.MessageUtil;
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
                log.info(MessageUtil.format("BooleanFutureTask fail", "taskName", taskName));
            } else {
                log.info(MessageUtil.format("BooleanFutureTask success", "taskName", taskName));
            }
        } catch (Throwable e) {
            countDown();
            if (e instanceof CancellationException) {
                log.warn(MessageUtil.format("BooleanFutureTask cancel", e, "taskName", taskName));
            } else if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            } else {
                log.error(MessageUtil.format("BooleanFutureTask error"), e, "taskName", taskName);
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    private void countDown() {
        for (int i = 0; i < countDownLatch.getCount() - 1; i++) {
            countDownLatch.countDown();
        }
    }
}
