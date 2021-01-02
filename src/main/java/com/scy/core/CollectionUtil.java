package com.scy.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.scy.core.encode.UrlEncodeUtil;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CollectionUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/29.
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    @Nullable
    public static <T> T firstElement(@Nullable List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Nullable
    public static <T> T lastElement(@Nullable List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static void shuffle(List<?> list) {
        Collections.shuffle(list);
    }

    /**
     * 无交集返回true
     */
    public static boolean disjoint(Collection<?> c1, Collection<?> c2) {
        return Collections.disjoint(c1, c2);
    }

    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    public static <T> Set<T> emptySet() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Lists.newArrayList(elements);
    }

    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        return Sets.newHashSet(elements);
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        return Lists.partition(list, size);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>(16);
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1) {
        HashMap<K, V> hashMap = newHashMap();
        hashMap.put(k1, v1);
        return hashMap;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2) {
        HashMap<K, V> hashMap = newHashMap(k1, v1);
        hashMap.put(k2, v2);
        return hashMap;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> hashMap = newHashMap(k1, v1, k2, v2);
        hashMap.put(k3, v3);
        return hashMap;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> hashMap = newHashMap(k1, v1, k2, v2, k3, v3);
        hashMap.put(k4, v4);
        return hashMap;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> hashMap = newHashMap(k1, v1, k2, v2, k3, v3, k4, v4);
        hashMap.put(k5, v5);
        return hashMap;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        HashMap<K, V> hashMap = newHashMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
        hashMap.put(k6, v6);
        return hashMap;
    }

    public static String map2Str(Map<String, ?> params, boolean urlEncode) {
        if (isEmpty(params)) {
            return StringUtil.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        Map<String, ?> treeMap = new TreeMap<>(params);
        treeMap.forEach((key, value) -> {
            String valueStr;
            if (urlEncode) {
                valueStr = UrlEncodeUtil.urlEncode(ObjectUtil.obj2Str(value).trim());
            } else {
                valueStr = ObjectUtil.obj2Str(value).trim();
            }
            result.append(key).append("=").append(valueStr).append("&");
        });
        return result.substring(0, result.length() - 1);
    }

    public static <T> List<T> unmodifiableList(List<? extends T> list) {
        return Collections.unmodifiableList(list);
    }

    public static <T> Set<T> unmodifiableSet(Set<? extends T> set) {
        return Collections.unmodifiableSet(set);
    }

    public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
        return Collections.unmodifiableMap(map);
    }

    public static <T> List<T> synchronizedList(List<T> list) {
        return Collections.synchronizedList(list);
    }

    public static <T> Set<T> synchronizedSet(Set<T> set) {
        return Collections.synchronizedSet(set);
    }

    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> map) {
        return Collections.synchronizedMap(map);
    }

    public static <T> List<T> enumeration2List(Enumeration<T> enumeration) {
        if (ObjectUtil.isNull(enumeration)) {
            return emptyList();
        }
        List<T> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    public static <T> List<T> emptyIfNull(final List<T> list) {
        return ListUtils.emptyIfNull(list);
    }

    /**
     * 交集
     */
    public static <E> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2) {
        return ListUtils.intersection(list1, list2);
    }

    public static <E> List<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
        return ListUtils.removeAll(collection, remove);
    }

    public static <T> Set<T> emptyIfNull(final Set<T> set) {
        return SetUtils.emptyIfNull(set);
    }

    public static <K, V> Map<K, V> emptyIfNull(final Map<K, V> map) {
        return MapUtils.emptyIfNull(map);
    }

    public static <K, V> V getObject(final Map<K, V> map, final K key, final V defaultValue) {
        return MapUtils.getObject(map, key, defaultValue);
    }

    public static <K> String getString(final Map<? super K, ?> map, final K key, final String defaultValue) {
        return MapUtils.getString(map, key, defaultValue);
    }

    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key, final Boolean defaultValue) {
        return MapUtils.getBoolean(map, key, defaultValue);
    }

    public static <K> Integer getInteger(final Map<? super K, ?> map, final K key, final Integer defaultValue) {
        return MapUtils.getInteger(map, key, defaultValue);
    }

    public static <K> Long getLong(final Map<? super K, ?> map, final K key, final Long defaultValue) {
        return MapUtils.getLong(map, key, defaultValue);
    }

    public static <K> Double getDouble(final Map<? super K, ?> map, final K key, final Double defaultValue) {
        return MapUtils.getDouble(map, key, defaultValue);
    }

    public static <T> Stream<T> stream(Collection<T> collection) {
        if (Objects.isNull(collection)) {
            return Stream.empty();
        }

        return collection.stream();
    }

    public static <T, R> Stream<R> map(Collection<T> collection, Function<T, R> mapper) {
        return stream(collection).filter(Objects::nonNull).map(mapper).filter(Objects::nonNull);
    }

    public static <T, K> Map<K, T> toMap(Stream<T> stream, Function<T, K> keyMapper) {
        return stream.collect(Collectors.toMap(keyMapper, Function.identity(), (oldValue, newValue) -> newValue));
    }

    public static <T, K, V> Map<K, V> toMap(Stream<T> stream, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return stream.collect(Collectors.toMap(keyMapper, valueMapper, (oldValue, newValue) -> newValue));
    }

    public static <K, V> V merge(Map<K, V> map, K key, V value, BiFunction<V, V, V> remappingFunction) {
        return map.merge(key, value, remappingFunction);
    }
}
