package com.scy.core.net;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.HttpURLConnection;
import java.util.Map;

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

    /**
     * 上传表单
     */
    private Map<String, Object> textParams;

    /**
     * 上传参数名称
     */
    private String fileParamName;

    /**
     * 上传文件名称
     */
    private String fileName;

    /**
     * 上传文件数据
     */
    private byte[] fileBytes;


    private HttpURLConnection httpUrlConnection;

    private long startTime;
}
