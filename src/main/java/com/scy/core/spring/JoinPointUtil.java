package com.scy.core.spring;

import com.scy.core.StringUtil;
import com.scy.core.model.JoinPointBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JoinPointUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/4.
 */
public class JoinPointUtil {

    private JoinPointUtil() {
    }

    private static final Pattern SPRING_VALUE = Pattern.compile("\\$\\{(.+)}");

    public static JoinPointBO getJoinPointBO(JoinPoint joinPoint) {
        JoinPointBO joinPointBO = new JoinPointBO();
        joinPointBO.setTarget(joinPoint.getTarget());
        joinPointBO.setTargetClass(joinPoint.getTarget().getClass());
        joinPointBO.setArgs(joinPoint.getArgs());
        joinPointBO.setMethod(((MethodSignature) joinPoint.getSignature()).getMethod());
        joinPointBO.setMethodName(joinPointBO.getMethod().getDeclaringClass().getName() + StringUtil.POINT + joinPointBO.getMethod().getName());
        return joinPointBO;
    }

    public static String parseValue(String value) {
        if (StringUtil.isEmpty(value)) {
            return StringUtil.EMPTY;
        }

        Matcher matcher = SPRING_VALUE.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).trim());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
