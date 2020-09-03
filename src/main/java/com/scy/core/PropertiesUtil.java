package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * PropertiesUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
@Slf4j
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    public static Properties loadAllProperties(String source) {
        try {
            return PropertiesLoaderUtils.loadAllProperties(source);
        } catch (Throwable e) {
            log.error(MessageUtil.format("loadAllProperties error", e, "source", source));
            return null;
        }
    }
}
