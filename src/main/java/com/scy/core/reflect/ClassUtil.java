package com.scy.core.reflect;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

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

    private static final HashMap<String, Class<?>> PRIM_CLASSES = new HashMap<>();

    static {
        PRIM_CLASSES.put(Boolean.TYPE.getName(), boolean.class);
        PRIM_CLASSES.put(Byte.TYPE.getName(), byte.class);
        PRIM_CLASSES.put(Character.TYPE.getName(), char.class);
        PRIM_CLASSES.put(Short.TYPE.getName(), short.class);
        PRIM_CLASSES.put(Integer.TYPE.getName(), int.class);
        PRIM_CLASSES.put(Long.TYPE.getName(), long.class);
        PRIM_CLASSES.put(Float.TYPE.getName(), float.class);
        PRIM_CLASSES.put(Double.TYPE.getName(), double.class);
        PRIM_CLASSES.put(Void.TYPE.getName(), void.class);
    }

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

    public static Class<?> resolveClass(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Class<?> cl = PRIM_CLASSES.get(className);
            if (cl != null) {
                return cl;
            } else {
                throw e;
            }
        }
    }
}
