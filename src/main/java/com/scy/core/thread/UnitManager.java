package com.scy.core.thread;

import com.scy.core.CollectionUtil;
import com.scy.core.ObjectUtil;
import com.scy.core.reflect.ClassUtil;
import com.scy.core.reflect.MethodUtil;
import com.scy.core.spring.ApplicationContextUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : shichunyang
 * Date    : 2022/6/23
 * Time    : 11:57 上午
 * ---------------------------------------
 * Desc    : UnitManager
 */
public class UnitManager {

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static void execute(List<Unit> units, Object object) {
        if (CollectionUtil.isEmpty(units) || Objects.isNull(object)) {
            return;
        }

        units.sort(Comparator.comparingInt(Unit::getPriority));

        units.forEach(unit -> run(unit, object));

        Field[] fields = object.getClass().getDeclaredFields();
        List<? extends CompletableFuture<?>> taskFutures = Stream.of(fields).filter(field -> {
            field.setAccessible(Boolean.TRUE);
            Class<?> fieldType = field.getType();
            return ObjectUtil.equals(fieldType, CompletableFuture.class);
        }).map(field -> {
            try {
                return (CompletableFuture<?>) field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        CompletableFuture.allOf(taskFutures.toArray(new CompletableFuture<?>[]{})).join();
    }

    private static void run(Unit unit, Object object) {
        try {
            Object bean = ApplicationContextUtil.getBean(ClassUtil.resolveClass(unit.getClassName()));
            Method method = MethodUtil.getMethod(bean.getClass(), unit.getMethodName(), ClassUtil.resolveClass(unit.getParameterTypes()));
            method.invoke(bean, object);

            unit.setStatus(Boolean.TRUE);
        } catch (Exception e) {
            unit.setStatus(Boolean.FALSE);
            unit.setThrowable(e);
        }
    }
}
