package com.scy.core.trace;

import com.scy.core.StringUtil;
import com.scy.core.UUIDUtil;
import com.scy.core.thread.ThreadLocalUtil;
import org.slf4j.MDC;

/**
 * TraceUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
public class TraceUtil {

    private TraceUtil() {
    }

    public static final String TRACE_ID = "trace_id";

    /**
     * 设置链路id
     */
    public static String setTraceId(String traceId) {
        if (StringUtil.isEmpty(traceId)) {
            traceId = UUIDUtil.uuid();
        }
        ThreadLocalUtil.put(TRACE_ID, traceId);
        setMdcTraceId();
        return traceId;
    }

    /**
     * 获取链路id
     */
    public static String getTraceId() {
        String traceId = (String) ThreadLocalUtil.get(TRACE_ID);
        if (StringUtil.isEmpty(traceId)) {
            return StringUtil.EMPTY;
        }
        return traceId;
    }

    /**
     * 清空链路变量
     */
    public static void clearTrace() {
        clearMdc();
        ThreadLocalUtil.clear();
    }

    /**
     * 给日志上下文添加链路id
     */
    public static void setMdcTraceId() {
        String traceId = getTraceId();
        if (StringUtil.isEmpty(traceId)) {
            return;
        }
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 清空日志上下文
     */
    public static void clearMdc() {
        MDC.clear();
    }

    public static void putMdc(String key, String val) {
        MDC.put(key, val);
    }
}
