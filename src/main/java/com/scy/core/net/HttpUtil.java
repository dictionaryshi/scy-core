package com.scy.core.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.scy.core.*;
import com.scy.core.format.MessageUtil;
import com.scy.core.json.JsonUtil;
import com.scy.core.spring.ApplicationContextUtil;
import com.scy.core.trace.TraceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static final String BOUNDARY_SPLIT = "--";

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

    public static <T> T httpRequest(HttpParam httpParam, TypeReference<T> typeReference) {
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

            return parseHttpResult(httpParam, typeReference);
        } catch (Throwable e) {
            log.error(MessageUtil.format("httpRequest error", e, "requestUrl", httpParam.getRequestUrl(), "requestBody", httpParam.getRequestBody()));
            return null;
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

    private static <T> T parseHttpResult(HttpParam httpParam, TypeReference<T> typeReference) throws Throwable {
        T result;
        int responseCode = httpParam.getHttpUrlConnection().getResponseCode();
        try (InputStream inputStream = httpParam.getHttpUrlConnection().getInputStream()) {
            Map<String, List<String>> responseHeaders = new HashMap<>(httpParam.getHttpUrlConnection().getHeaderFields());
            responseHeaders.remove(null);
            result = JsonUtil.json2Object(inputStream, typeReference);
            long endTime = System.currentTimeMillis();
            log.info(MessageUtil.format("parseHttpResult",
                    "requestUrl", httpParam.getRequestUrl(), "requestBody", httpParam.getRequestBody(), "responseCode", responseCode,
                    "result", result, "responseHeaders", responseHeaders, StringUtil.COST, (endTime - httpParam.getStartTime())));
        }
        return result;
    }

    public static <T> T get(String requestUrl, Map<String, Object> params, TypeReference<T> typeReference, HttpOptions httpOptions) {
        String requestParam = StringUtil.EMPTY;
        if (!CollectionUtil.isEmpty(params)) {
            requestParam = StringUtil.QUESTION + CollectionUtil.map2Str(params, Boolean.TRUE);
        }
        HttpParam httpParam = new HttpParam();
        httpParam.setRequestUrl(requestUrl + requestParam);
        httpParam.setRequestMethod(GET);
        httpParam.setRequestBody(null);
        httpParam.setHttpOptions(httpOptions);
        T result = HttpUtil.httpRequest(httpParam, typeReference);
        if (httpOptions.getRetry() <= 0) {
            return result;
        }
        if (Objects.nonNull(result)) {
            return result;
        }
        return HttpUtil.httpRequest(httpParam, typeReference);
    }

    public static <T> T post(String requestUrl, Map<String, Object> params, TypeReference<T> typeReference, HttpOptions options) {
        return request(requestUrl, POST, params, typeReference, options);
    }

    public static <T> T put(String requestUrl, Map<String, Object> params, TypeReference<T> typeReference, HttpOptions options) {
        return request(requestUrl, PUT, params, typeReference, options);
    }

    public static <T> T delete(String requestUrl, Map<String, Object> params, TypeReference<T> typeReference, HttpOptions options) {
        return request(requestUrl, DELETE, params, typeReference, options);
    }

    private static <T> T request(String requestUrl, String requestMethod, Map<String, Object> params, TypeReference<T> typeReference, HttpOptions httpOptions) {
        HttpParam httpParam = new HttpParam();
        httpParam.setRequestUrl(requestUrl);
        httpParam.setRequestMethod(requestMethod);
        String requestBody = null;
        if (!CollectionUtil.isEmpty(params)) {
            if (ObjectUtil.equals(httpOptions.getContentType(), APPLICATION_FORM_URLENCODED_VALUE)) {
                requestBody = CollectionUtil.map2Str(params, Boolean.TRUE);
            } else {
                requestBody = JsonUtil.object2Json(params);
            }
        }
        httpParam.setRequestBody(requestBody);
        httpParam.setHttpOptions(httpOptions);
        return HttpUtil.httpRequest(httpParam, typeReference);
    }

    public static <T> T upload(HttpParam httpParam, TypeReference<T> typeReference) {
        HttpURLConnection httpUrlConnection = null;

        OutputStream outputStream = null;

        httpParam.setRequestMethod(POST);

        String boundary = UUIDUtil.uuid();
        httpParam.setHttpOptions(httpParam.getHttpOptions().contentType("multipart/form-data; boundary=" + boundary));

        try {
            // 获取连接对象
            httpUrlConnection = getHttpUrlConnection(httpParam);

            httpParam.setStartTime(System.currentTimeMillis());

            // 发起连接
            httpUrlConnection.connect();

            outputStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());

            if (!CollectionUtil.isEmpty(httpParam.getTextParams())) {
                StringBuilder sb = new StringBuilder();
                httpParam.getTextParams().forEach((name, dataObj) -> {
                    String data = ObjectUtil.obj2Str(dataObj);
                    if (StringUtil.isEmpty(data)) {
                        return;
                    }
                    sb.append(BOUNDARY_SPLIT).append(boundary).append(StringUtil.CRLF);
                    sb.append("Content-Disposition: form-data; name=\"" + name + "\"").append(StringUtil.CRLF).append(StringUtil.CRLF);
                    sb.append(data).append(StringUtil.CRLF);
                });
                outputStream.write(sb.toString().getBytes());
            }

            if (!ObjectUtil.isNull(httpParam.getFileBytes())) {
                StringBuilder sb = new StringBuilder();
                sb.append(BOUNDARY_SPLIT).append(boundary).append(StringUtil.CRLF);
                sb.append("Content-Disposition: form-data; name=\"" + httpParam.getFileParamName() + "\"; filename=\"" + httpParam.getFileName() + "\";").append(StringUtil.CRLF).append(StringUtil.CRLF);
                outputStream.write(sb.toString().getBytes());
                outputStream.write(httpParam.getFileBytes());
            }

            outputStream.write((StringUtil.CRLF + BOUNDARY_SPLIT + boundary + BOUNDARY_SPLIT + StringUtil.CRLF + StringUtil.CRLF).getBytes());
            outputStream.flush();

            httpParam.setHttpUrlConnection(httpUrlConnection);

            return parseHttpResult(httpParam, typeReference);
        } catch (Throwable e) {
            log.error(MessageUtil.format("upload error", e, "requestUrl", httpParam.getRequestUrl()));
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable e) {
                    log.error(MessageUtil.format("upload outputStream close error", e, "requestUrl", httpParam.getRequestUrl()));
                }
            }

            if (httpUrlConnection != null) {
                // 释放连接
                httpUrlConnection.disconnect();
            }
        }
    }
}
