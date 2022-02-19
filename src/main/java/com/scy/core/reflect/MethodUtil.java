package com.scy.core.reflect;

import com.scy.core.ObjectUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.model.MethodParam;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : shichunyang
 * Date    : 2022/2/19
 * Time    : 5:39 下午
 * ---------------------------------------
 * Desc    : MethodUtil
 */
@Slf4j
public class MethodUtil {

    public static final Map<MethodParam, Method> METHOD_MAP = new ConcurrentHashMap<>();

    public static Method getMethod(Class<?> beanClass, String methodName, Class<?>[] parameterTypes) {
        MethodParam methodParam = new MethodParam(beanClass, methodName, parameterTypes);

        Method targetMethod = METHOD_MAP.get(methodParam);
        if (!ObjectUtil.isNull(targetMethod)) {
            return targetMethod;
        }

        try {
            Method method = beanClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(Boolean.TRUE);

            METHOD_MAP.putIfAbsent(methodParam, method);

            return method;
        } catch (Exception e) {
            log.error(MessageUtil.format("getMethod error", e, "methodName", methodName));
            return null;
        }
    }

    public static void putMethod(Method method) {
        MethodParam methodParam = new MethodParam(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
        METHOD_MAP.putIfAbsent(methodParam, method);
    }
}
