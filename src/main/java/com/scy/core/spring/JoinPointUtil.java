package com.scy.core.spring;

import com.scy.core.StringUtil;
import com.scy.core.model.JoinPointBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

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
        joinPointBO.setMethod(((MethodSignature) joinPoint.getSignature()).getMethod());
        joinPointBO.setMethodName(joinPointBO.getMethod().getDeclaringClass().getName() + StringUtil.POINT + joinPointBO.getMethod().getName());
        return joinPointBO;
    }
}
