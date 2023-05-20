package com.scy.core.exception;

import com.github.rholder.retry.*;
import com.scy.core.format.MessageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author : shichunyang
 * Date    : 2021/11/16
 * Time    : 1:46 下午
 * ---------------------------------------
 * Desc    : Try Catch
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Try {

    public static <T> T of(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            log.error(MessageUtil.format("Try Catch", e));
            return null;
        }
    }

    public static <T> T of(Supplier<T> supplier, int retries) {
        try {
            Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                    .retryIfExceptionOfType(Throwable.class)
                    .withWaitStrategy(WaitStrategies.fixedWait(100, TimeUnit.MILLISECONDS))
                    .withStopStrategy(StopStrategies.stopAfterAttempt(retries + 1))
                    .build();

            return retryer.call(supplier::get);
        } catch (Throwable e) {
            log.error(MessageUtil.format("Try Catch", e, "retries", retries));
            return null;
        }
    }

    public static void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            log.error(MessageUtil.format("Try Catch", e));
        }
    }
}
