package com.scy.core.encode;

import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * UrlEncodeUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/29.
 */
@Slf4j
public class UrlEncodeUtil {

    private UrlEncodeUtil() {
    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, SystemUtil.CHARSET_UTF_8_STR);
        } catch (Throwable e) {
            log.error(MessageUtil.format("urlEncode error", e, "str", str));
            return StringUtil.EMPTY;
        }
    }

    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, SystemUtil.CHARSET_UTF_8_STR);
        } catch (Exception e) {
            log.error(MessageUtil.format("urlDecode error", e, "str", str));
            return StringUtil.EMPTY;
        }
    }
}
