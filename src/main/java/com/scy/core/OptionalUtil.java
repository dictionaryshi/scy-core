package com.scy.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author : shichunyang
 * Date    : 2021/1/21
 * Time    : 3:45 下午
 * ---------------------------------------
 * Desc    : Optional工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionalUtil {

    public static <T> Optional<T> empty() {
        return Optional.empty();
    }

    public static <T> Optional<T> ofNullable(T value) {
        return Optional.ofNullable(value);
    }

    public static <T> boolean isPresent(Optional<T> optional) {
        return optional.isPresent();
    }

    public static <T> void ifPresent(Optional<T> optional, Consumer<T> consumer) {
        optional.ifPresent(consumer);
    }

    public static <T> Optional<T> filter(Optional<T> optional, Predicate<T> predicate) {
        return optional.filter(predicate);
    }

    public static <T, U> Optional<U> map(Optional<T> optional, Function<T, U> mapper) {
        return optional.map(mapper);
    }

    public static <T, U> Optional<U> flatMap(Optional<T> optional, Function<T, Optional<U>> mapper) {
        return optional.flatMap(mapper);
    }

    public static <T> T orElseGet(Optional<T> optional, Supplier<T> supplier) {
        return optional.orElseGet(supplier);
    }

    public static <T, X extends Throwable> T orElseThrow(Optional<T> optional, Supplier<X> exceptionSupplier) throws X {
        return optional.orElseThrow(exceptionSupplier);
    }
}
