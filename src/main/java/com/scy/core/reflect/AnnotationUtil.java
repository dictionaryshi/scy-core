package com.scy.core.reflect;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * AnnotationUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/28.
 */
public class AnnotationUtil {

    private AnnotationUtil() {
    }

    @Nullable
    public static <T extends Annotation> T findAnnotation(AnnotatedElement annotatedElement, @Nullable Class<T> annotationType) {
        return AnnotationUtils.findAnnotation(annotatedElement, annotationType);
    }
}
