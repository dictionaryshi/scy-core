package com.scy.core.thread;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.*;
import java.util.function.*;

/**
 * @author : shichunyang
 * Date    : 2021/1/1
 * Time    : 9:37 下午
 * ---------------------------------------
 * Desc    : CompletableFutureUtil
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletableFutureUtil {

    /**
     * 创建一个异步操作
     */
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static <U> CompletableFuture<U> completedFuture(U value) {
        return CompletableFuture.completedFuture(value);
    }

    public static <U> U get(CompletableFuture<U> completableFuture) throws InterruptedException, ExecutionException {
        return completableFuture.get();
    }

    public static <U> U get(CompletableFuture<U> completableFuture, long timeout)
            throws InterruptedException, ExecutionException, TimeoutException {
        return completableFuture.get(timeout, TimeUnit.MILLISECONDS);
    }

    public static <U> U join(CompletableFuture<U> completableFuture) {
        return completableFuture.join();
    }

    public static <U> U getNow(CompletableFuture<U> completableFuture, U valueIfAbsent) {
        return completableFuture.getNow(valueIfAbsent);
    }

    public static <U> boolean complete(CompletableFuture<U> completableFuture, U value) {
        return completableFuture.complete(value);
    }

    public static <U> boolean completeExceptionally(CompletableFuture<U> completableFuture, Throwable ex) {
        return completableFuture.completeExceptionally(ex);
    }

    public static <T, U> CompletableFuture<U> thenApply(CompletableFuture<T> completableFuture, Function<T, U> fn) {
        return completableFuture.thenApply(fn);
    }

    public static <T> CompletableFuture<Void> thenAccept(CompletableFuture<T> completableFuture, Consumer<T> action) {
        return completableFuture.thenAccept(action);
    }

    public static <T, U, V> CompletableFuture<V> thenCombine(
            CompletableFuture<T> completableFuture,
            CompletionStage<U> other,
            BiFunction<T, U, V> fn) {
        return completableFuture.thenCombine(other, fn);
    }

    public static <T, U> CompletableFuture<U> applyToEither(
            CompletableFuture<T> completableFuture,
            CompletionStage<T> other,
            Function<T, U> fn) {
        return completableFuture.applyToEither(other, fn);
    }

    public static <T, U> CompletableFuture<U> thenCompose(
            CompletableFuture<T> completableFuture, Function<T, CompletionStage<U>> fn) {
        return completableFuture.thenCompose(fn);
    }

    /**
     * 计算结果完成时的回调方法
     */
    public static <T> CompletableFuture<T> whenComplete(
            CompletableFuture<T> completableFuture, BiConsumer<T, Throwable> action) {
        return completableFuture.whenComplete(action);
    }

    /**
     * 执行任务完成时对结果的处理
     */
    public static <T, U> CompletableFuture<U> handle(
            CompletableFuture<T> completableFuture, BiFunction<T, Throwable, U> fn) {
        return completableFuture.handle(fn);
    }

    /**
     * 计算结果完成时的回调方法
     */
    public static <T> CompletableFuture<T> exceptionally(
            CompletableFuture<T> completableFuture, Function<Throwable, T> fn) {
        return completableFuture.exceptionally(fn);
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.allOf(cfs);
    }

    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.anyOf(cfs);
    }
}
