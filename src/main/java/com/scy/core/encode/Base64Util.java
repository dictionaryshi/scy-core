package com.scy.core.encode;

import org.apache.commons.codec.binary.Base64;

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
        return Base64.encodeBase64String(str.getBytes());
    }

    public static String base64Decode(String str) {
        return new String(Base64.decodeBase64(str));
    }
}
