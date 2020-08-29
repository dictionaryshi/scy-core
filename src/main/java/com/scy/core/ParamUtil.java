package com.scy.core;

import com.scy.core.format.MessageUtil;
import com.scy.core.reflect.ClassUtil;
import com.scy.core.spring.ApplicationContextUtil;
import com.scy.core.spring.AutoConfigurationPackageUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ParamUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/28.
 */
@Slf4j
public class ParamUtil {

    private ParamUtil() {
    }

    public static boolean isStartWithBasePackage(Class<?> paramClass) {
        String className = paramClass.getName();
        if (className.startsWith(SystemUtil.BASE_PACKAGE)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isStartWithAutoConfigurationPackage(Class<?> paramClass) {
        String className = paramClass.getName();
        List<String> autoConfigurationPackages = AutoConfigurationPackageUtil.getAutoConfigurationPackage(ApplicationContextUtil.getApplicationContext());
        for (String autoConfigurationPackage : autoConfigurationPackages) {
            if (className.startsWith(autoConfigurationPackage)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean isBasicParam(Class<?> paramClass) {
        if (ClassUtil.isAssignable(String.class, paramClass)) {
            return Boolean.TRUE;
        }

        if (ClassUtil.isAssignable(Number.class, paramClass)) {
            return Boolean.TRUE;
        }

        if (isStartWithBasePackage(paramClass)) {
            return Boolean.TRUE;
        }

        if (isStartWithAutoConfigurationPackage(paramClass)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public static void trimParam(Object param) {
        if (ObjectUtil.isNull(param)) {
            return;
        }

        Class<?> paramClass = param.getClass();
        if (!isStartWithBasePackage(paramClass) && !isStartWithAutoConfigurationPackage(paramClass)) {
            return;
        }

        Field[] fields = paramClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(Boolean.TRUE);
            try {
                Object valueObject = field.get(param);
                if (ObjectUtil.isNull(valueObject)) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (!ObjectUtil.equals(fieldType, String.class) && !(valueObject instanceof String)) {
                    continue;
                }
                field.set(param, ((String) valueObject).trim());
            } catch (Throwable e) {
                log.error(MessageUtil.format("trimParam error", e, "field", field.getName(), "param", param));
            }
        }
    }
}
