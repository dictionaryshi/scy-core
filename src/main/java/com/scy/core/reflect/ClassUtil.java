package com.scy.core.reflect;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;

/**
 * ClassUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/22.
 */
public class ClassUtil {

    private ClassUtil() {
    }

    public static final String UNCHECKED = "unchecked";

    /**
     * 获取类加载器
     */
    @Nullable
    public static ClassLoader getDefaultClassLoader() {
        return ClassUtils.getDefaultClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> resolveClassName(String className, @Nullable ClassLoader classLoader)
            throws IllegalArgumentException {
        return ClassUtils.resolveClassName(className, classLoader);
    }

    /**
     * 是否可以将右侧类型的值分配给左侧
     */
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        return ClassUtils.isAssignable(lhsType, rhsType);
    }

    /**
     * 是否可以把值赋给指定类型
     */
    public static boolean isAssignableValue(Class<?> type, @Nullable Object value) {
        return ClassUtils.isAssignableValue(type, value);
    }

    /**
     * 获取类的所有接口
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return ClassUtils.getAllInterfacesForClass(clazz);
    }

    /**
     * 获取类的包名
     */
    public static String getPackageName(Class<?> clazz) {
        return ClassUtils.getPackageName(clazz);
    }

    /**
     * 获取类的包名
     */
    public static String getPackageName(String clazzName) {
        return ClassUtils.getPackageName(clazzName);
    }

    /**
     * 获取泛型
     */
    @SuppressWarnings(UNCHECKED)
    public static <T> Class<T> getGenericType(Class<?> clazz) {
        return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
