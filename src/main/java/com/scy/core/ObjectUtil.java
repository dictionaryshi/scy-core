package com.scy.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.scy.core.enums.ResponseCodeEnum;
import com.scy.core.exception.BusinessException;
import com.scy.core.format.DateUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * ObjectUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/19.
 */
@Slf4j
public class ObjectUtil {

    private ObjectUtil() {
    }

    public static String obj2Str(Object object) {
        if (Objects.isNull(object)) {
            return StringUtil.EMPTY;
        }

        if (object instanceof String) {
            return (String) object;
        }

        if (object instanceof Date) {
            return DateUtil.date2Str((Date) object, DateUtil.PATTERN_SECOND);
        }

        return JsonUtil.object2Json(object);
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (isNull(obj)) {
            throw new BusinessException(ResponseCodeEnum.PARAMS_EXCEPTION.getCode(), message);
        }
        return obj;
    }

    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return Boolean.TRUE;
        }

        if (obj instanceof String) {
            return StringUtil.isEmpty((String) obj);
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return ArrayUtil.isEmpty(obj);
        }

        if (obj instanceof JsonNode) {
            return JsonUtil.isEmpty((JsonNode) obj);
        }

        return Boolean.FALSE;
    }

    public static Integer obj2Int(Object object, Integer defaultValue) {
        if (ObjectUtil.isNull(object)) {
            return defaultValue;
        }

        if (object instanceof Number) {
            return ((Number) object).intValue();
        }

        if (object instanceof String) {
            String numberStr = ((String) object).trim();
            if (StringUtil.isEmpty(numberStr)) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(numberStr);
            } catch (Exception e) {
                log.error(MessageUtil.format("obj2Int error", e, "object", object));
                return defaultValue;
            }
        }

        return defaultValue;
    }

    public static Double obj2Double(Object object, Double defaultValue) {
        if (ObjectUtil.isNull(object)) {
            return defaultValue;
        }

        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }

        if (object instanceof String) {
            String numberStr = ((String) object).trim();
            if (StringUtil.isEmpty(numberStr)) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(numberStr);
            } catch (Exception e) {
                log.error(MessageUtil.format("obj2Double error", e, "object", object));
                return defaultValue;
            }
        }

        return defaultValue;
    }

    public static Long obj2Long(Object object, Long defaultValue) {
        if (ObjectUtil.isNull(object)) {
            return defaultValue;
        }

        if (object instanceof Number) {
            return ((Number) object).longValue();
        }

        if (object instanceof String) {
            String numberStr = ((String) object).trim();
            if (StringUtil.isEmpty(numberStr)) {
                return defaultValue;
            }
            try {
                return Long.parseLong(numberStr);
            } catch (Exception e) {
                log.error(MessageUtil.format("obj2Long error", e, "object", object));
                return defaultValue;
            }
        }

        return defaultValue;
    }
}
