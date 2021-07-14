package com.scy.core;

import com.google.common.collect.Lists;
import com.scy.core.format.MessageUtil;
import com.scy.core.json.JsonUtil;
import com.scy.core.model.DiffBO;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * DiffUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/21.
 */
@Slf4j
public class DiffUtil {

    private DiffUtil() {
    }

    /**
     * 获取对象的差异
     */
    public static List<DiffBO> diff(Object before, Object after) {
        List<DiffBO> result = Lists.newArrayList();

        Field[] fields = after.getClass().getDeclaredFields();
        Stream.of(fields).forEach(field -> {
            field.setAccessible(Boolean.TRUE);

            String property = field.getName();

            Object beforeValue;
            Object afterValue;
            try {
                beforeValue = field.get(before);
                afterValue = field.get(after);
            } catch (Throwable e) {
                log.error(MessageUtil.format("field get error", e,
                        "property", property,
                        "before", JsonUtil.object2Json(before),
                        "after", JsonUtil.object2Json(after)
                ));
                return;
            }

            if (ObjectUtil.isNull(afterValue)) {
                return;
            }

            if (ObjectUtil.equals(beforeValue, afterValue)) {
                return;
            }

            DiffBO diffBO = new DiffBO();
            diffBO.setProperty(property);
            diffBO.setBefore(beforeValue);
            diffBO.setAfter(afterValue);
            result.add(diffBO);
        });

        return result;
    }

    public static boolean isDiff(Object before, Object after) {
        return !CollectionUtil.isEmpty(diff(before, after));
    }

    public static Map<String, Object> flatMap(Map<String, Object> map) {
        if (CollectionUtil.isEmpty(map)) {
            return Collections.emptyMap();
        }

        return map.entrySet().stream()
                .filter(entry -> Objects.nonNull(entry) && Objects.nonNull(entry.getKey()))
                .flatMap(DiffUtil::flatEntry)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), TreeMap::putAll);
    }

    private static Stream<Map.Entry<String, Object>> flatEntry(Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof Map<?, ?>) {
            return ((Map<?, ?>) entry.getValue()).entrySet().stream()
                    .flatMap(e -> flatEntry(new AbstractMap.SimpleEntry<>(entry.getKey() + StringUtil.POINT + e.getKey(), e.getValue())));
        }

        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + StringUtil.POINT + i, list.get(i)))
                    .flatMap(DiffUtil::flatEntry);
        }

        return Stream.of(entry);
    }
}
