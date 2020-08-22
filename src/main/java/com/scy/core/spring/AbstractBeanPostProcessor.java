package com.scy.core.spring;

import com.scy.core.exception.BusinessException;
import com.scy.core.reflect.ClassUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * AbstractBeanPostProcessor
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/22.
 */
public class AbstractBeanPostProcessor<T> implements BeanPostProcessor {

    private Class<T> beanType;

    public AbstractBeanPostProcessor() {
        try {
            this.beanType = ClassUtil.getGenericType(this.getClass());
        } catch (Throwable e1) {
            try {
                @SuppressWarnings(ClassUtil.UNCHECKED)
                Class<T> genericSuperclass = (Class<T>) this.getClass().getGenericSuperclass();
                this.beanType = ClassUtil.getGenericType(genericSuperclass);
            } catch (Throwable e2) {
                throw new BusinessException("AbstractBeanPostProcessor error", e2);
            }
        }
    }

    @SuppressWarnings(ClassUtil.UNCHECKED)
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ClassUtil.isAssignableValue(beanType, bean)) {
            this.before((T) bean, beanName);
        }
        return bean;
    }

    @SuppressWarnings(ClassUtil.UNCHECKED)
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ClassUtil.isAssignableValue(beanType, bean)) {
            this.after((T) bean, beanName);
        }
        return bean;
    }

    protected void before(T bean, String beanName) {
    }

    protected void after(T bean, String beanName) {
    }
}
