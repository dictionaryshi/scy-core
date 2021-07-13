package com.scy.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.aviator.AviatorEvaluator;
import com.scy.core.format.MessageUtil;
import com.scy.core.json.JsonUtil;
import com.scy.core.reflect.ClassUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 动态表达式
 *
 * @author shichunyang
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AviatorUtil {

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static <T> T execute(String expression, Object obj) {
        try {
            return (T) AviatorEvaluator.execute(expression, JsonUtil.convertValue(obj, new TypeReference<HashMap<String, Object>>() {
            }), Boolean.TRUE);
        } catch (Exception e) {
            log.error(MessageUtil.format("AviatorUtil.execute error", e, "expression", expression, "obj", obj));
            return null;
        }
    }
}
