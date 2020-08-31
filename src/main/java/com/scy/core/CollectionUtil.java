package com.scy.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.scy.core.encode.UrlEncodeUtil;
import org.springframework.lang.Nullable;

import java.util.*;

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
}
