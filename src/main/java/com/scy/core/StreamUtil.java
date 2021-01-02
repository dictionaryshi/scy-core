package com.scy.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author : shichunyang
 * Date    : 2021/1/2
 * Time    : 1:10 下午
 * ---------------------------------------
 * Desc    : StreamUtil
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtil {

    public static <T> Stream<T> filter(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate);
    }

    public static <T, R> Stream<R> map(Stream<T> stream, Function<T, R> mapper) {
        return stream.map(mapper);
    }

    public static <T, R> Stream<R> flatMap(Stream<T> stream, Function<T, Stream<R>> mapper) {
        return stream.flatMap(mapper);
    }

    public static <T> Stream<T> distinct(Stream<T> stream) {
        return stream.distinct();
    }

    public static <T> Stream<T> sorted(Stream<T> stream, Comparator<T> comparator) {
        return stream.sorted(comparator);
    }

    public static <T> Stream<T> peek(Stream<T> stream, Consumer<T> action) {
        return stream.peek(action);
    }

    public static <T> Stream<T> limit(Stream<T> stream, long maxSize) {
        return stream.limit(maxSize);
    }

    public static <T> void forEach(Stream<T> stream, Consumer<T> action) {
        stream.forEach(action);
    }

    public static <T> void forEachOrdered(Stream<T> stream, Consumer<T> action) {
        stream.forEachOrdered(action);
    }

    public static <T> T reduce(Stream<T> stream, T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    public static <T> Optional<T> reduce(Stream<T> stream, BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    public static <T> Optional<T> min(Stream<T> stream, Comparator<T> comparator) {
        return stream.min(comparator);
    }

    public static <T> Optional<T> max(Stream<T> stream, Comparator<T> comparator) {
        return stream.max(comparator);
    }

    public static <T> long count(Stream<T> stream) {
        return stream.count();
    }

    public static <T> boolean anyMatch(Stream<T> stream, Predicate<T> predicate) {
        return stream.anyMatch(predicate);
    }

    public static <T> boolean allMatch(Stream<T> stream, Predicate<T> predicate) {
        return stream.allMatch(predicate);
    }

    public static <T> boolean noneMatch(Stream<T> stream, Predicate<T> predicate) {
        return stream.noneMatch(predicate);
    }

    public static <T> Optional<T> findFirst(Stream<T> stream) {
        return stream.findFirst();
    }

    public static <T> Optional<T> findAny(Stream<T> stream) {
        return stream.findAny();
    }

    @SafeVarargs
    public static <T> Stream<T> of(T... values) {
        return Stream.of(values);
    }
}
