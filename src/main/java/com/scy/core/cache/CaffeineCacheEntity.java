package com.scy.core.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CaffeineCacheEntity<K, V> {

    private final Integer refreshTimeSec;
    private final Integer expireTimeSec;
    private final Integer initialCapacity;
    private final Integer maximumSize;
    private final ThreadPoolExecutor threadPool;
    private final Function<K, V> sourceCall;
    private AsyncLoadingCache<K, V> cache;

    public CaffeineCacheEntity(Integer refreshTimeSec, Integer expireTimeSec, Integer initialCapacity, Integer maximumSize, ThreadPoolExecutor threadPool, Function<K, V> sourceCall) {
        if (refreshTimeSec >= expireTimeSec) {
            throw new IllegalArgumentException("refreshTimeSec must be less than expireTimeSec");
        }

        this.refreshTimeSec = refreshTimeSec;
        this.expireTimeSec = expireTimeSec;
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
        this.threadPool = threadPool;
        this.sourceCall = sourceCall;
    }

    public void init() {
        cache = Caffeine.newBuilder()
                .refreshAfterWrite(refreshTimeSec, TimeUnit.SECONDS)
                .expireAfterWrite(expireTimeSec, TimeUnit.SECONDS)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .executor(threadPool)
                .recordStats()
                .buildAsync((key, executor) -> CompletableFuture.supplyAsync(() -> sourceCall.apply(key), executor));
    }

    public V getValue(K key) {
        V value = cache.get(key).join();
        CacheStats stats = cache.synchronous().stats();
        System.out.println(value);
        System.out.println(stats.hitRate());
        return value;
    }

    public V getSourceCall(K key) {
        return sourceCall.apply(key);
    }
}
