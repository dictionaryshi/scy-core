package com.scy.core.net;

import com.scy.core.CollectionUtil;
import com.scy.core.ObjectUtil;
import com.scy.core.StringUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.spring.ApplicationContextUtil;
import com.scy.core.trace.TraceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
@Slf4j
public class HttpUtil {

    private HttpUtil() {
    }

    public static final int CONNECT_TIMEOUT = 5_000;

    public static final int READ_TIMEOUT = 10_000;

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String PUT = "PUT";

    public static final String DELETE = "DELETE";

    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

    public static final String APPLICATION_JSON_VALUE = "application/json";

    public static final String USER_AGENT = "User-Agent";

    public static final String CONNECTION = "Connection";

    public static final String ACCEPT = "Accept";

    public static final String ALL_VALUE = "*/*";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String KEEP_ALIVE = "keep-alive";

    private static HttpURLConnection getHttpUrlConnection(HttpParam httpParam) throws Throwable {
        URL url = new URL(httpParam.getRequestUrl());

        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod(httpParam.getRequestMethod());
        httpUrlConnection.setConnectTimeout(httpParam.getHttpOptions().getConnectTimeout());
        httpUrlConnection.setReadTimeout(httpParam.getHttpOptions().getReadTimeout());
        httpUrlConnection.setDoInput(Boolean.TRUE);
        httpUrlConnection.setDoOutput(Boolean.TRUE);
        httpUrlConnection.setUseCaches(Boolean.FALSE);
        httpUrlConnection.setInstanceFollowRedirects(Boolean.TRUE);

        if (!StringUtil.isEmpty(httpParam.getRequestBody())) {
            httpUrlConnection.setFixedLengthStreamingMode(httpParam.getRequestBody().getBytes().length);
        }

        httpUrlConnection.setRequestProperty(TraceUtil.TRACE_ID, TraceUtil.getTraceId());
        httpUrlConnection.setRequestProperty(USER_AGENT, ApplicationContextUtil.getApplicationName());
        httpUrlConnection.setRequestProperty(CONNECTION, KEEP_ALIVE);
        httpUrlConnection.setRequestProperty(ACCEPT, ALL_VALUE);
        httpUrlConnection.setRequestProperty(CONTENT_TYPE, httpParam.getHttpOptions().getContentType());

        if (!CollectionUtil.isEmpty(httpParam.getHttpOptions().getHeaders())) {
            httpParam.getHttpOptions().getHeaders().forEach((key, value) -> httpUrlConnection.setRequestProperty(key, ObjectUtil.obj2Str(value)));
        }

        return httpUrlConnection;
    }

    public static String httpRequest(HttpParam httpParam) {
        HttpURLConnection httpUrlConnection = null;

        OutputStream outputStream = null;

        try {
            // 获取连接对象
            httpUrlConnection = getHttpUrlConnection(httpParam);

            httpParam.setStartTime(System.currentTimeMillis());

            // 发起连接
            httpUrlConnection.connect();

            if (!StringUtil.isEmpty(httpParam.getRequestBody())) {
                // 发送数据
                outputStream = httpUrlConnection.getOutputStream();
                outputStream.write(httpParam.getRequestBody().getBytes());
                outputStream.flush();
            }

            httpParam.setHttpUrlConnection(httpUrlConnection);

        } catch (Throwable e) {
            log.error(MessageUtil.format("httpRequest error", e, "requestUrl", httpParam.getRequestUrl(), "requestBody", httpParam.getRequestBody()));
            return StringUtil.EMPTY;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable e) {
                    log.error(MessageUtil.format("httpRequest outputStream close error", e, "requestUrl", httpParam.getRequestUrl(), "requestBody", httpParam.getRequestBody()));
                }
            }

            if (httpUrlConnection != null) {
                // 释放连接
                httpUrlConnection.disconnect();
            }
        }
    }
}
