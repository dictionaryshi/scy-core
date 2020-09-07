package com.scy.core.net;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.HttpURLConnection;

/**
 * HttpParam
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
@Getter
@Setter
@ToString
public class HttpParam {

    private String requestUrl;

    private String requestMethod;

    private String requestBody;

    private HttpOptions httpOptions;


    private HttpURLConnection httpUrlConnection;

    private long startTime;
}
