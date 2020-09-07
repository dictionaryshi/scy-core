package com.scy.core.net;

import lombok.extern.slf4j.Slf4j;

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
}
