package com.scy.core.format;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * MessageFormatUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
public class MessageFormatUtil {

    private MessageFormatUtil() {
    }

    /**
     * 格式化字符串
     */
    public static String format(String pattern, Object... arguments) {
        // 将所有数字类型参数转为字符串
        arguments = Arrays.stream(arguments)
                .map(arg -> (arg instanceof Number) ? String.valueOf(arg) : arg)
                .toArray();
        return MessageFormat.format(pattern, arguments);
    }
}
