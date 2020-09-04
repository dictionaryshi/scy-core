package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * JoinPointBO
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/4.
 */
@Getter
@Setter
@ToString
public class JoinPointBO {

    private Object target;

    private Class<?> targetClass;

    private Object[] args;

    private Method method;

    private String methodName;
}
