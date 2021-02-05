package com.scy.core.reflect;

import com.scy.core.CollectionUtil;
import com.scy.core.ObjectUtil;
import com.scy.core.spring.ApplicationContextUtil;
import com.scy.core.spring.AutoConfigurationPackageUtil;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.lang.Nullable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * ReflectionsUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
public class ReflectionsUtil {

    private ReflectionsUtil() {
    }

    public static final String ALL_PACKAGE = CollectionUtil.firstElement(AutoConfigurationPackageUtil.getAutoConfigurationPackage(ApplicationContextUtil.getApplicationContext()));

    public static volatile Reflections classReflections;

    public static volatile Reflections fieldReflections;

    static {
        if (ObjectUtil.isNull(classReflections)) {
            synchronized (ReflectionsUtil.class) {
                if (ObjectUtil.isNull(classReflections)) {
                    classReflections = new Reflections(new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forPackage(ALL_PACKAGE))
                            .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));
                }
            }
        }

        if (ObjectUtil.isNull(fieldReflections)) {
            synchronized (ReflectionsUtil.class) {
                if (ObjectUtil.isNull(fieldReflections)) {
                    fieldReflections = new Reflections(new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forPackage(ALL_PACKAGE))
                            .setScanners(new FieldAnnotationsScanner()));
                }
            }
        }
    }

    public static Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        return classReflections.getTypesAnnotatedWith(annotation);
    }

    public static Set<Field> getFieldsAnnotatedWith(final Class<? extends Annotation> annotation) {
        return fieldReflections.getFieldsAnnotatedWith(annotation);
    }

    public static void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fieldCallback, @Nullable ReflectionUtils.FieldFilter fieldFilter) {
        ReflectionUtils.doWithFields(clazz, fieldCallback, fieldFilter);
    }

    public static boolean isPublicStaticFinal(Field field) {
        return ReflectionUtils.isPublicStaticFinal(field);
    }

    public static void setField(Object targetObject, String name, @Nullable Object value) {
        ReflectionTestUtils.setField(targetObject, name, value);
    }

    public static void setField(Class<?> targetClass, String name, @Nullable Object value) {
        ReflectionTestUtils.setField(targetClass, name, value);
    }

    @Nullable
    public static Object getField(Object targetObject, String name) {
        return ReflectionTestUtils.getField(targetObject, name);
    }

    @Nullable
    public static Object getField(Class<?> targetClass, String name) {
        return ReflectionTestUtils.getField(targetClass, name);
    }
}
