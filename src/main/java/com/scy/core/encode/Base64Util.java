package com.scy.core.encode;

import com.scy.core.SystemUtil;
import com.scy.core.exception.BusinessException;
import com.scy.core.format.MessageUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Base64Util
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/30.
 */
public class Base64Util {

    private Base64Util() {
    }

    public static String base64Encode(String str) {
        try {
            return Base64.encodeBase64String(str.getBytes(SystemUtil.CHARSET_UTF_8_STR));
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(MessageUtil.format("base64Encode error", "str", str), e);
        }
    }

    public static String base64Decode(String str) {
        return new String(Base64.decodeBase64(str));
    }
}
