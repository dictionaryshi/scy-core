package com.scy.core;

import com.scy.core.enums.ResponseCodeEnum;
import com.scy.core.exception.BusinessException;
import com.scy.core.format.DateUtil;
import com.scy.core.json.JsonUtil;

import java.util.Date;
import java.util.Objects;

/**
 * ObjectUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/19.
 */
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
}
