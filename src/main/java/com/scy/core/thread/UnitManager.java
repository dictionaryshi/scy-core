package com.scy.core.thread;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.enums.ResponseCodeEnum;
import com.scy.core.reflect.ClassUtil;
import com.scy.core.reflect.MethodUtil;
import com.scy.core.spring.ApplicationContextUtil;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author : shichunyang
 * Date    : 2022/6/23
 * Time    : 11:57 上午
 * ---------------------------------------
 * Desc    : UnitManager
 */
public class UnitManager {

    public static void execute(List<Unit> units, Object object) {
        if (CollectionUtil.isEmpty(units) || Objects.isNull(object)) {
            return;
        }

        units.sort(Comparator.comparingInt(Unit::getPriority));

        units.forEach(unit -> ThreadPoolUtil.getThreadPool(unit.getPoolName(),
                unit.getCorePoolSize(), unit.getMaxPoolSize(), unit.getQueueSize()));

        List<CompletableFuture<Void>> taskFutures = units.stream().map(unit -> {
            if (StringUtil.isEmpty(unit.getPoolName())) {
                run(unit, object);
                return null;
            }

            ThreadPoolExecutor threadPool = ThreadPoolUtil.getThreadPool(unit.getPoolName(),
                    unit.getCorePoolSize(), unit.getMaxPoolSize(), unit.getQueueSize());
            return CompletableFuture.runAsync(() -> run(unit, object), threadPool);
        }).filter(Objects::nonNull).collect(Collectors.toList());

        CompletableFuture.allOf(taskFutures.toArray(new CompletableFuture<?>[]{})).join();
    }

    private static void run(Unit unit, Object object) {
        int retry = unit.getRetry() + 1;
        for (int i = 0; i < retry; i++) {
            try {
                Object bean = ApplicationContextUtil.getBean(ClassUtil.resolveClass(unit.getClassName()));
                Method method = MethodUtil.getMethod(bean.getClass(), unit.getMethodName(), ClassUtil.resolveClass(unit.getParameterTypes()));
                method.invoke(bean, object);

                unit.setStatus(Boolean.TRUE);
                unit.setCode(ResponseCodeEnum.SUCCESS.getCode());
            } catch (Exception e) {
                unit.setStatus(Boolean.FALSE);
                unit.setCode(ResponseCodeEnum.SYSTEM_EXCEPTION.getCode());
                unit.setThrowable(e);
            }
        }
    }
}
