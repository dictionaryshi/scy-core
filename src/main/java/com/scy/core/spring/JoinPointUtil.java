package com.scy.core.spring;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.model.JoinPointBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * JoinPointUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/4.
 */
public class JoinPointUtil {

    private JoinPointUtil() {
    }

    public static JoinPointBO getJoinPointBO(JoinPoint joinPoint) {
        JoinPointBO joinPointBO = new JoinPointBO();
        joinPointBO.setTarget(joinPoint.getTarget());
        joinPointBO.setTargetClass(joinPoint.getTarget().getClass());
        joinPointBO.setArgs(joinPoint.getArgs());

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Map<String, Object> params = CollectionUtil.newHashMap();
        AtomicInteger index = new AtomicInteger();
        Stream.of(parameterNames).forEach(parameterName -> params.put(parameterName, joinPoint.getArgs()[index.getAndIncrement()]));
        joinPointBO.setParams(params);

        joinPointBO.setMethod(methodSignature.getMethod());
        joinPointBO.setMethodName(joinPointBO.getMethod().getDeclaringClass().getName() + StringUtil.POINT + joinPointBO.getMethod().getName());
        return joinPointBO;
    }
}
