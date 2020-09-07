package com.scy.core.net;

import com.scy.core.SystemUtil;
import com.scy.core.format.NumberUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpOptions
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
@Getter
@ToString
public class HttpOptions {

    private int connectTimeout = HttpUtil.CONNECT_TIMEOUT;

    private int readTimeout = HttpUtil.READ_TIMEOUT;

    private String contentType = HttpUtil.APPLICATION_FORM_URLENCODED_VALUE;

    private String responseCharset = SystemUtil.CHARSET_UTF_8_STR;

    private int retry = NumberUtil.ZERO.intValue();

    private final Map<String, Object> headers = new HashMap<>(16);

    public static HttpOptions build() {
        return new HttpOptions();
    }

    public HttpOptions connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpOptions readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpOptions contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpOptions responseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
        return this;
    }

    public HttpOptions retry(int retry) {
        this.retry = retry;
        return this;
    }

    public HttpOptions putHeader(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }
}
