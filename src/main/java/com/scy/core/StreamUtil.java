package com.scy.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    public static <T, U extends Comparable<U>> Comparator<T> comparing(Function<T, U> keyExtractor) {
        return Comparator.comparing(keyExtractor);
    }

    public static <T> Stream<T> stream(T[] array) {
        return Arrays.stream(array);
    }

    public static <T, K> Map<K, List<T>> groupingBy(Stream<T> stream, Function<T, K> function) {
        return stream.collect(Collectors.groupingBy(function));
    }

    public static <T, K, D> Map<K, Set<D>> groupingBy(Stream<T> stream, Function<T, K> keyFunction, Function<T, D> mapper) {
        return stream.collect(Collectors.groupingBy(keyFunction, Collectors.mapping(mapper, Collectors.toSet())));
    }

    public static <T> Map<Boolean, List<T>> partitioningBy(Stream<T> stream, Predicate<T> predicate) {
        return stream.collect(Collectors.partitioningBy(predicate));
    }

    public static <T> String join(Stream<T> stream, Function<T, String> function, String delimiter) {
        return stream.map(function).collect(Collectors.joining(delimiter));
    }

    public static IntStream range(int startInclusive, int endExclusive) {
        return IntStream.range(startInclusive, endExclusive);
    }
}
