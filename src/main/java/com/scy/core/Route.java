package com.scy.core;

import com.scy.core.format.DateUtil;
import com.scy.core.format.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 5:34 下午
 * ---------------------------------------
 * Desc    : Route
 */
public class Route {

    private static final ConcurrentMap<String, AtomicInteger> COUNTER_MAP = new ConcurrentHashMap<>();

    private static long cacheValidTime = 0;

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
        } else {
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
}
