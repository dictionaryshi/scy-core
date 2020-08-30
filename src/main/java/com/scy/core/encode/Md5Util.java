package com.scy.core.encode;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Md5Util
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/30.
 */
public class Md5Util {

    private Md5Util() {
    }

    public static String md5Encode(String str) {
        return DigestUtils.md5Hex(str);
    }
}
