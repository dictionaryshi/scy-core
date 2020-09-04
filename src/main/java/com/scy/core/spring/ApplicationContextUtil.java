package com.scy.core.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

/**
 * ApplicationContextUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/27.
 */
public class ApplicationContextUtil {

    private ApplicationContextUtil() {
    }

    public static final String SPRING = "spring";

    public static final String APPLICATION_NAME = "spring.application.name";

    public static final String ACTIVE = AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

    public static final String ENV_DEV = "dev";

    public static final String ENV_STAG = "stag";

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
