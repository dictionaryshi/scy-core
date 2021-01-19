package com.scy.core;

import com.google.common.collect.Lists;
import com.scy.core.format.MessageUtil;
import com.scy.core.json.JsonUtil;
import com.scy.core.model.DiffBO;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
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
}
