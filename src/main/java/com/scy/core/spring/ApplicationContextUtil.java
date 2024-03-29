package com.scy.core.spring;

import com.scy.core.ObjectUtil;
import com.scy.core.StringUtil;
import com.scy.core.format.DateUtil;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        if (ObjectUtil.isNull(applicationContext)) {
            return StringUtil.EMPTY;
        }
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

    public static Object getTarget(Object proxy) throws Exception {
        Advised advised = (Advised) proxy;
        return advised.getTargetSource().getTarget();
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
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

    public static MutablePropertySources getMutablePropertySources() {
        return ((ConfigurableEnvironment) applicationContext.getEnvironment()).getPropertySources();
    }

    public static void addLastMapPropertySource(String name, Map<String, Object> source) {
        getMutablePropertySources().addLast(new MapPropertySource(name, source));
    }

    public static void replaceMapPropertySource(String name, Map<String, Object> source) {
        getMutablePropertySources().replace(name, new MapPropertySource(name, source));
    }

    public static <T> T registerBean(String name, Class<T> clazz, T object, Object... args) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        BeanDefinitionBuilder beanDefinitionBuilder;
        if (Objects.isNull(object)) {
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        } else {
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz, () -> object);
        }

        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        beanFactory.setAllowBeanDefinitionOverriding(Boolean.TRUE);

        beanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());

        beanFactory.setAllowBeanDefinitionOverriding(Boolean.FALSE);

        return applicationContext.getBean(name, clazz);
    }
}
