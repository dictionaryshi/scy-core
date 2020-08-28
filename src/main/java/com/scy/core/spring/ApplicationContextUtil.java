package com.scy.core.spring;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContextUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/27.
 */
public class ApplicationContextUtil {

    private ApplicationContextUtil() {
    }

    private volatile static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (ApplicationContextUtil.applicationContext == null) {
            synchronized (ApplicationContextUtil.class) {
                if (ApplicationContextUtil.applicationContext == null) {
                    ApplicationContextUtil.applicationContext = applicationContext;
                }
            }
        }
    }
}
