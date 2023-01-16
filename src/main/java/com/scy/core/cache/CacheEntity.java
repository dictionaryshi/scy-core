package com.scy.core.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author : shichunyang
 * Date    : 2023/1/16
 * Time    : 5:54 下午
 * ---------------------------------------
 * Desc    : CacheEntity
 */
public class CacheEntity<K, V> {

    private final Integer refreshTimeSec;

    private final Integer expireTimeSec;

    private final Integer initialCapacity;

    private final Integer maximumSize;

    private final ThreadPoolExecutor threadPool;

    private final Function<K, V> sourceCall;

    private LoadingCache<K, V> cache;

    public CacheEntity(Integer refreshTimeSec, Integer expireTimeSec, Integer initialCapacity, Integer maximumSize, ThreadPoolExecutor threadPool, Function<K, V> sourceCall) {
        this.refreshTimeSec = refreshTimeSec;
        this.expireTimeSec = expireTimeSec;
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
        this.threadPool = threadPool;
        this.sourceCall = sourceCall;
    }

    public void init() {
        cache = CacheBuilder.newBuilder()
                // refreshTimeSec要小于expireTimeSec
                .refreshAfterWrite(refreshTimeSec, TimeUnit.SECONDS)
                .expireAfterWrite(expireTimeSec, TimeUnit.SECONDS)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build(
                        CacheLoader.asyncReloading(new CacheLoader<K, V>() {
                            @Override
                            public V load(K key) throws Exception {
                                return sourceCall.apply(key);
                            }
                        }, threadPool)
                );
    }

    public V getValue(K key) throws Exception {
        return cache.get(key);
    }

    public V getSourceCall(K key) throws Exception {
        return sourceCall.apply(key);
    }
}
