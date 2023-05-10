package com.scy.core;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.scy.core.format.DateUtil;
import com.scy.core.format.NumberUtil;
import com.scy.core.util.LruCache;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 5:34 下午
 * ---------------------------------------
 * Desc    : Route
 */
public class Route {

    private static final ConcurrentMap<String, LinkedHashMap<String, String>> LRU_MAP = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, AtomicInteger> COUNTER_MAP = new ConcurrentHashMap<>();

    private static long cacheValidTime = 0;

    private static final int VIRTUAL_NODE_NUM = 100;

    public static <T> T first(TreeSet<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        return set.first();
    }

    public static <T> T last(TreeSet<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        return set.last();
    }

    public static <T> T routeLoop(String serviceKey, TreeSet<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        List<T> list = new ArrayList<>(set);

        if (System.currentTimeMillis() > cacheValidTime) {
            COUNTER_MAP.clear();
            cacheValidTime = System.currentTimeMillis() + DateUtil.DAY;
        }

        AtomicInteger counter = COUNTER_MAP.get(serviceKey);
        if (ObjectUtil.isNull(counter) || counter.get() > 100_0000) {
            counter = new AtomicInteger(RandomUtil.nextInt(0, list.size()));
            COUNTER_MAP.put(serviceKey, counter);
        }

        int index = NumberUtil.modulo(counter.getAndIncrement(), list.size());

        return list.get(index);
    }

    public static <T> T routeRandom(TreeSet<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        List<T> list = new ArrayList<>(set);

        int index = RandomUtil.nextInt(0, list.size());

        return list.get(index);
    }

    public static <T> T routeConsistentHash(String clientKey, TreeSet<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        TreeMap<Long, T> nodeMap = new TreeMap<>();

        set.forEach(node -> {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                long nodeHash = ObjectUtil.hash("SHARD-" + node + "-NODE-" + i);
                nodeMap.put(nodeHash, node);
            }
        });

        long serviceKeyHash = ObjectUtil.hash(clientKey);

        SortedMap<Long, T> tailMap = nodeMap.tailMap(serviceKeyHash);
        if (!tailMap.isEmpty()) {
            return tailMap.get(tailMap.firstKey());
        }

        return nodeMap.firstEntry().getValue();
    }

    public static <T> T busyOrFailSkip(TreeSet<T> set, Predicate<T> predicate) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }

        return set.stream().filter(predicate).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static String lru(String serviceKey, TreeSet<String> set) {
        LinkedHashMap<String, String> lruCache = LRU_MAP.get(serviceKey);
        if (Objects.isNull(lruCache)) {
            lruCache = new LruCache<>(10000);
            LRU_MAP.putIfAbsent(serviceKey, lruCache);
        }

        // put new
        for (String address : set) {
            if (!lruCache.containsKey(address)) {
                lruCache.put(address, address);
            }
        }

        // remove old
        List<String> delKeys = new ArrayList<>();
        for (String existKey : lruCache.keySet()) {
            if (!set.contains(existKey)) {
                delKeys.add(existKey);
            }
        }
        if (!delKeys.isEmpty()) {
            for (String delKey : delKeys) {
                lruCache.remove(delKey);
            }
        }

        String eldestKey = lruCache.entrySet().iterator().next().getKey();
        return lruCache.get(eldestKey);
    }

    public static <T> T routeRandomWeight(TreeMap<T, Double> weightMap) {
        Pair<Double, Map<T, Range<Double>>> pair = weightInit(weightMap);

        double totalWeight = pair.getLeft();
        Map<T, Range<Double>> weightsMap = pair.getRight();

        double random = ThreadLocalRandom.current().nextDouble() * totalWeight;

        // 判断结果落在哪个区间
        for (Map.Entry<T, Range<Double>> entry : weightsMap.entrySet()) {
            if (entry.getValue().contains(random)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private static <T> Pair<Double, Map<T, Range<Double>>> weightInit(TreeMap<T, Double> weightMap) {
        Map<T, Range<Double>> weightsMap = Maps.newHashMap();

        // 区间起点
        double rangeFrom = 0D;

        // 总权重
        double totalWeight = 0D;

        for (Map.Entry<T, Double> entry : weightMap.entrySet()) {
            if (entry.getValue() <= 0D) {
                continue;
            }

            totalWeight += entry.getValue();

            // 区间终点(开区间)
            double rangeTo = rangeFrom + entry.getValue();

            weightsMap.put(entry.getKey(), Range.closedOpen(rangeFrom, rangeTo));

            rangeFrom = rangeTo;
        }

        return Pair.of(totalWeight, weightsMap);
    }
}
