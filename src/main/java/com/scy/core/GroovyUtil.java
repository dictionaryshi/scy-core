package com.scy.core;

import com.scy.core.encode.Md5Util;
import com.scy.core.format.MessageUtil;
import com.scy.core.reflect.ClassUtil;
import com.scy.core.spring.SpringInjectUtil;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : shichunyang
 * Date    : 2022/6/10
 * Time    : 5:13 下午
 * ---------------------------------------
 * Desc    : GroovyUtil
 */
@Slf4j
public class GroovyUtil {

    private static final GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader();

    private static final ConcurrentMap<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, GroovyObject> OBJECT_CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static <T> T parseClass(String codeSource) {
        try {
            String md5Str = Md5Util.md5Encode(codeSource);

            Class<?> clazz = CLASS_CACHE.get(md5Str);
            if (Objects.isNull(clazz)) {
                clazz = GROOVY_CLASS_LOADER.parseClass(codeSource);
                CLASS_CACHE.putIfAbsent(md5Str, clazz);
            }

            GroovyObject object = OBJECT_CACHE.get(md5Str);
            if (Objects.isNull(object)) {
                object = (GroovyObject) clazz.newInstance();
                SpringInjectUtil.injectService(object);
                OBJECT_CACHE.putIfAbsent(md5Str, object);
            }

            return (T) object;
        } catch (Exception e) {
            log.error(MessageUtil.format("parseClass error", e, "codeSource", codeSource));
            return null;
        }
    }
}
