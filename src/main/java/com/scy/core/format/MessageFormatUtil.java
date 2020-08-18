package com.scy.core.format;

import java.text.MessageFormat;

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
        return MessageFormat.format(pattern, arguments);
    }
}
