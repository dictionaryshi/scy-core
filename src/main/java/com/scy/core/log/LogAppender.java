package com.scy.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.Map;

/**
 * LogAppender
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/6.
 */
public class LogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    public void append(ILoggingEvent loggingEvent) {
        String threadName = loggingEvent.getThreadName();
        String levelStr = loggingEvent.getLevel().levelStr;
        String formattedMessage = loggingEvent.getFormattedMessage();
        String loggerName = loggingEvent.getLoggerName();
        String appkey = loggingEvent.getLoggerContextVO().getName();

        StackTraceElement stackTraceElement = loggingEvent.getCallerData()[0];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        Map<String, String> mdcPropertyMap = loggingEvent.getMDCPropertyMap();
        long timeStamp = loggingEvent.getTimeStamp();
    }
}
