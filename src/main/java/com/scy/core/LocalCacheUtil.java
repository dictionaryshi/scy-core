package com.scy.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : shichunyang
 * Date    : 2022/6/27
 * Time    : 11:55 上午
 * ---------------------------------------
 * Desc    : LocalCacheUtil
 */
public class LocalCacheUtil {

    private static final ConcurrentMap<String, LocalCacheData> LOCAL_CACHE = new ConcurrentHashMap<>();

    @Getter
    @Setter
    @ToString
    private static class LocalCacheData {

        private String key;

        private Object value;

        private long timeout;

        public LocalCacheData() {
        }

        public LocalCacheData(String key, Object value, long timeout) {
            this.key = key;
            this.value = value;
            this.timeout = timeout;
        }
    }


    public static boolean set(String key, Object value, long timeout) {
        cleanTimeoutCache();

        if (StringUtil.isEmpty(key)) {
            return Boolean.FALSE;
        }

        if (Objects.isNull(value)) {
            remove(key);
        }

        if (timeout <= 0) {
            remove(key);
        }

        long timeoutTime = System.currentTimeMillis() + timeout;

        LocalCacheData localCacheData = new LocalCacheData(key, value, timeoutTime);
        LOCAL_CACHE.put(localCacheData.getKey(), localCacheData);

        return Boolean.TRUE;
    }

    public static boolean remove(String key) {
        if (StringUtil.isEmpty(key)) {
            return Boolean.FALSE;
        }

        LOCAL_CACHE.remove(key);

        return Boolean.TRUE;
    }

    public static Object get(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        LocalCacheData localCacheData = LOCAL_CACHE.get(key);
        if (Objects.nonNull(localCacheData) && System.currentTimeMillis() < localCacheData.getTimeout()) {
            return localCacheData.getValue();
        }

        remove(key);

        return null;
    }

    public static void cleanTimeoutCache() {
        if (LOCAL_CACHE.isEmpty()) {
            return;
        }

        Set<Map.Entry<String, LocalCacheData>> entries = LOCAL_CACHE.entrySet();
        entries.forEach(entry -> {
            LocalCacheData localCacheData = entry.getValue();
            if (Objects.nonNull(localCacheData) && System.currentTimeMillis() >= localCacheData.getTimeout()) {
                LOCAL_CACHE.remove(entry.getKey());
            }
        });
    }
}
