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

    private final LinkedHashMap<String, Object> params = new LinkedHashMap<>();

    private MessageUtil(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    public static String format(String message, Throwable throwable) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1, String k2, Object v2) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        messageUtil.params.put(k2, v2);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        messageUtil.params.put(k2, v2);
        messageUtil.params.put(k3, v3);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        messageUtil.params.put(k2, v2);
        messageUtil.params.put(k3, v3);
        messageUtil.params.put(k4, v4);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        messageUtil.params.put(k2, v2);
        messageUtil.params.put(k3, v3);
        messageUtil.params.put(k4, v4);
        messageUtil.params.put(k5, v5);
        return messageUtil.toString();
    }

    public static String format(String message, Throwable throwable, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6, Object v6) {
        MessageUtil messageUtil = new MessageUtil(message, throwable);
        messageUtil.params.put(k1, v1);
        messageUtil.params.put(k2, v2);
        messageUtil.params.put(k3, v3);
        messageUtil.params.put(k4, v4);
        messageUtil.params.put(k5, v5);
        messageUtil.params.put(k6, v6);
        return messageUtil.toString();
    }

    public static String format(String message) {
        return format(message, null);
    }

    public static String format(String message, String k1, Object v1) {
        return format(message, null, k1, v1);
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2) {
        return format(message, null, k1, v1, k2, v2);
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return format(message, null, k1, v1, k2, v2, k3, v3);
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return format(message, null, k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
        return format(message, null, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5, String k6, Object v6) {
        return format(message, null, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(StringUtil.SPACE).append(StringUtil.SPACE);

        if (!params.isEmpty()) {
            params.forEach((key, valueObject) -> {
                String value = ObjectUtil.obj2Str(valueObject);
                sb.append(key).append(StringUtil.ARROW).append(StringUtil.replaceCrLf(value, StringUtil.SPACE)).append(StringUtil.COMMA).append(StringUtil.SPACE);
            });
        }

        sb.deleteCharAt(sb.length() - 2);

        if (!Objects.isNull(throwable)) {
            sb.append(SystemUtil.SYSTEM_LINE_BREAK).append(ExceptionUtil.getExceptionMessageWithTraceId(throwable));
        }
        return sb.toString();
    }
}
