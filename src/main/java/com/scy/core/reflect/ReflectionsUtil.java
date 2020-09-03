package com.scy.core.reflect;

import com.scy.core.ObjectUtil;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.lang.Nullable;
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

    public static final String ALL_PACKAGE = ".";

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

    public Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        return classReflections.getTypesAnnotatedWith(annotation);
    }

    public Set<Field> getFieldsAnnotatedWith(final Class<? extends Annotation> annotation) {
        return fieldReflections.getFieldsAnnotatedWith(annotation);
    }

    public static void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fieldCallback, @Nullable ReflectionUtils.FieldFilter fieldFilter) {
        ReflectionUtils.doWithFields(clazz, fieldCallback, fieldFilter);
    }

    public static boolean isPublicStaticFinal(Field field) {
        return ReflectionUtils.isPublicStaticFinal(field);
    }
}
