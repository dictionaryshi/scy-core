package com.scy.core.exception;

import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import com.scy.core.trace.TraceUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.stream.Stream;

/**
 * ExceptionUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static String[] getExceptionMessages(Throwable throwable) {
        return ExceptionUtils.getStackFrames(throwable);
    }

    public static String getExceptionMessageWithTraceId(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        String[] exceptionMessages = getExceptionMessages(throwable);
        String traceId = TraceUtil.getTraceId();
        Stream.of(exceptionMessages).forEach(exception -> sb.append(traceId).append(StringUtil.SPACE).append(exception.trim()).append(SystemUtil.SYSTEM_LINE_BREAK));
        return sb.toString();
    }
}
