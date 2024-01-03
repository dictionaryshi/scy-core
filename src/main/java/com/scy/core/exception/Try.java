package com.scy.core.exception;

import com.github.rholder.retry.*;
import com.scy.core.format.MessageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
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

    public static <T> T of(Supplier<T> supplier, int retries, int delayTime) throws ExecutionException, RetryException {
        WaitStrategy waitStrategy = attempt -> {
            // 获取当前尝试的次数
            long attemptNumber = attempt.getAttemptNumber();
            // 计算指数退避的延迟时间
            double exponentialDelay = Math.pow(2, attemptNumber - 1) * delayTime;
            // 转换为long类型，并确保不超过最大延迟
            return Math.min((long) exponentialDelay, 60_000L);
        };

        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfExceptionOfType(Throwable.class)
                .withWaitStrategy(waitStrategy)
                .withStopStrategy(StopStrategies.stopAfterAttempt(retries + 1))
                .build();

        return retryer.call(supplier::get);
    }

    public static void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            log.error(MessageUtil.format("Try Catch", e));
        }
    }
}
