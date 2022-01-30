package com.scy.core;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 12:35 上午
 * ---------------------------------------
 * Desc    : EnumUtil
 */
public class EnumUtil {

    private EnumUtil() {
    }

    public static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
        return EnumUtils.getEnumList(enumClass);
    }

    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
        return EnumUtils.getEnumMap(enumClass);
    }
}
