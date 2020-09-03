package com.scy.core.util;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRUCache
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/2.
 */
@Slf4j
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    /**
     * 缓存大小
     */
    private final int CACHE_SIZE;

    public LruCache(int cacheSize) {
        // true表示让LinkedHashMap按照访问顺序来进行排序, 最近访问的放在头部
        super(cacheSize, 0.75f, Boolean.TRUE);
        CACHE_SIZE = cacheSize;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当map中的数据量大于指定的缓存个数的时候, 就自动删除最老的数据
        boolean isDelete = size() > CACHE_SIZE;
        if (isDelete) {
            log.warn(MessageUtil.format("removeEldestEntry", "eldest", eldest));
        }
        return isDelete;
    }
}
