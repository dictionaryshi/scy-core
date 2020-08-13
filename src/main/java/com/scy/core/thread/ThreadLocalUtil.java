package com.scy.core.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ThreadLocalUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/13.
 */
public class ThreadLocalUtil {

    private ThreadLocalUtil() {
    }

    private static final ThreadLocalMap THREAD_LOCAL_MAP = new ThreadLocalMap();

    private static class ThreadLocalMap extends TransmittableThreadLocal<Map<String, Object>> {

        @Override
        protected Map<String, Object> initialValue() {
            return Maps.newHashMap();
        }
    }

    public static Map<String, Object> getThreadLocalMap() {
        return THREAD_LOCAL_MAP.get();
    }

    public static void put(String key, Object value) {
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        threadLocalMap.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get(key);
    }

    public static Object remove(String key) {
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.remove(key);
    }

    public static void clear() {
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        threadLocalMap.clear();
        THREAD_LOCAL_MAP.remove();
    }
}
