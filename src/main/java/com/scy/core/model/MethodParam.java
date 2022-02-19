package com.scy.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : shichunyang
 * Date    : 2022/2/19
 * Time    : 5:41 下午
 * ---------------------------------------
 * Desc    : MethodParam
 */
@Data
public class MethodParam implements Serializable {

    private Class<?> beanClass;

    private String methodName;

    private Class<?>[] parameterTypes;

    public MethodParam() {
    }

    public MethodParam(Class<?> beanClass, String methodName, Class<?>[] parameterTypes) {
        this.beanClass = beanClass;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }
}
