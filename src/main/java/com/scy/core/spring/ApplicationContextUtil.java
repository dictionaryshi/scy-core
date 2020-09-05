package com.scy.core.spring;

import com.scy.core.format.DateUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ApplicationContextUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/27.
 */
public class ApplicationContextUtil {

    private ApplicationContextUtil() {
    }

    public static final String SPRING = "spring";

    public static final String APPLICATION_NAME = "spring.application.name";

    public static final String ACTIVE = AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

    public static final String ENV_DEV = "dev";

    public static final String ENV_STAG = "stag";

    private volatile static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (ApplicationContextUtil.applicationContext == null) {
            synchronized (ApplicationContextUtil.class) {
                if (ApplicationContextUtil.applicationContext == null) {
                    ApplicationContextUtil.applicationContext = applicationContext;
                }
            }
        }
    }

    public static String getApplicationName() {
        return applicationContext.getId();
    }

    public static String getStartupDate() {
        return DateUtil.date2Str(new Date(applicationContext.getStartupDate()), DateUtil.PATTERN_SECOND);
    }

    public static List<String> getActiveProfiles() {
        return Stream.of(applicationContext.getEnvironment().getActiveProfiles()).collect(Collectors.toList());
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean containsBeanDefinition(String beanName) {
        return applicationContext.containsBeanDefinition(beanName);
    }

    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    public static String[] getBeanNamesForType(@Nullable Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeanNamesForAnnotation(annotationType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    @Nullable
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
        return applicationContext.findAnnotationOnBean(beanName, annotationType);
    }
}
