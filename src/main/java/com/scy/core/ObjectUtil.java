package com.scy.core;

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
}
