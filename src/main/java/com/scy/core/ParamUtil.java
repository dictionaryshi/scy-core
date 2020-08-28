package com.scy.core;

import com.scy.core.reflect.ClassUtil;
import com.scy.core.spring.AutoConfigurationPackageUtil;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * ParamUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/28.
 */
public class ParamUtil {

    private ParamUtil() {
    }

    public static boolean isBasicParam(Class<?> paramClass, ApplicationContext applicationContext) {
        if (ClassUtil.isAssignable(String.class, paramClass)) {
            return Boolean.TRUE;
        }

        if (ClassUtil.isAssignable(Number.class, paramClass)) {
            return Boolean.TRUE;
        }

        String className = paramClass.getName();
        if (className.startsWith(SystemUtil.BASE_PACKAGE)) {
            return Boolean.TRUE;
        }

        if (!ObjectUtil.isNull(applicationContext)) {
            List<String> autoConfigurationPackages = AutoConfigurationPackageUtil.getAutoConfigurationPackage(applicationContext);
            for (String autoConfigurationPackage : autoConfigurationPackages) {
                if (className.startsWith(autoConfigurationPackage)) {
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }
}
