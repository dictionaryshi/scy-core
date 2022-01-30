package com.scy.core;

import com.scy.core.format.NumberUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 5:34 下午
 * ---------------------------------------
 * Desc    : Route
 */
public class Route {

    public static <T> T routeLoop(List<T> list, AtomicLong counter) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        int index = NumberUtil.modulo(counter.getAndIncrement(), list.size());

        return list.get(index);
    }

    public static <T> T routeRandom(List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        int index = RandomUtil.nextInt(0, list.size());

        return list.get(index);
    }
}
