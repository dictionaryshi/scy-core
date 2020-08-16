package com.scy.core;

import java.util.UUID;

/**
 * UUIDUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/16.
 */
public class UUIDUtil {

    private UUIDUtil() {
    }

    public static String uuid() {
        return StringUtil.replace(UUID.randomUUID().toString(), "-", "").toUpperCase();
    }
}
