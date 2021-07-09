package com.scy.core.proxy;

import com.scy.core.reflect.ClassUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author : shichunyang
 * Date    : 2021/7/9
 * Time    : 12:43 下午
 * ---------------------------------------
 * Desc    : 代理工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProxyUtil {

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static <T> T newProxyInstance(Class<T> interfaceClass, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, invocationHandler);
    }
}
