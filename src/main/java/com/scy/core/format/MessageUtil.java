package com.scy.core.format;

import com.scy.core.ObjectUtil;
import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import com.scy.core.exception.ExceptionUtil;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * MessageUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
public class MessageUtil {

    private final String message;

    private final Throwable throwable;

    private final LinkedHashMap<Object, Object> params;

    private MessageUtil(String message, Throwable throwable, Object... keyValuePairs) {
        this.message = message;
        this.throwable = throwable;
        this.params = new LinkedHashMap<>();

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            if (i + 1 < keyValuePairs.length) {
                Object key = keyValuePairs[i];
                Object value = keyValuePairs[i + 1];
                this.params.put(key, value);
            }
        }
    }

    public static String format(String message, Throwable throwable, Object... keyValuePairs) {
        MessageUtil messageUtil = new MessageUtil(message, throwable, keyValuePairs);
        return messageUtil.toString();
    }

    public static String format(String message, Object... keyValuePairs) {
        return format(message, null, keyValuePairs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(message);

        if (!params.isEmpty()) {
            sb.append(StringUtil.SPACE);

            params.forEach((key, valueObject) -> {
                String value = ObjectUtil.obj2Str(valueObject);
                sb.append(key).append(StringUtil.ARROW).append(StringUtil.replaceCrLf(value, StringUtil.SPACE)).append(StringUtil.COMMA).append(StringUtil.SPACE);
            });

            sb.setLength(sb.length() - 2);
        }

        if (!Objects.isNull(throwable)) {
            sb.append(SystemUtil.SYSTEM_LINE_BREAK).append(ExceptionUtil.getExceptionMessageWithTraceId(throwable));
        }
        return sb.toString();
    }
}
