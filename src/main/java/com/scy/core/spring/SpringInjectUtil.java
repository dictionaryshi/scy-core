package com.scy.core.spring;

import com.scy.core.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author : shichunyang
 * Date    : 2022/6/10
 * Time    : 8:01 下午
 * ---------------------------------------
 * Desc    : SpringInjectUtil
 */
public class SpringInjectUtil {

    public static void injectService(Object instance) {
        if (Objects.isNull(instance)) {
            return;
        }

        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        if (Objects.isNull(applicationContext)) {
            return;
        }

        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object fieldBean = null;

            Resource resource = AnnotationUtils.getAnnotation(field, Resource.class);
            if (Objects.nonNull(resource)) {
                try {
                    if (!StringUtil.isEmpty(resource.name())) {
                        fieldBean = applicationContext.getBean(resource.name());
                    } else {
                        fieldBean = applicationContext.getBean(field.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Objects.isNull(fieldBean)) {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            } else if (Objects.nonNull(AnnotationUtils.getAnnotation(field, Autowired.class))) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class);
                if (Objects.nonNull(qualifier) && !StringUtil.isEmpty(qualifier.value())) {
                    fieldBean = applicationContext.getBean(qualifier.value());
                } else {
                    fieldBean = applicationContext.getBean(field.getType());
                }
            }

            if (Objects.nonNull(fieldBean)) {
                field.setAccessible(Boolean.TRUE);
                try {
                    field.set(instance, fieldBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
