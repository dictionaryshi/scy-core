package com.scy.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.scy.core.ObjectUtil;
import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import com.scy.core.format.DateUtil;
import com.scy.core.format.MessageUtil;
import com.scy.core.reflect.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * JsonUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/16.
 */
@Slf4j
public class JsonUtil {

    private JsonUtil() {
    }

    public static final String JSON = "json";

    private static final ObjectMapper OBJECT_MAPPER = getBaseObjectMapper();

    public static ObjectMapper getBaseObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_SECOND));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setTimeZone(SystemUtil.TIME_ZONE_SHANG_HAI);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        return mapper;
    }

    public static String object2Json(Object object) {
        if (Objects.isNull(object)) {
            return StringUtil.EMPTY;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error(MessageUtil.format("object2Json error", e, "object", object.toString()));
            return StringUtil.EMPTY;
        }
    }

    /**
     * 尽量使用其as API(带默认值)
     */
    public static JsonNode json2JsonNode(String json) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (Exception e) {
            log.error(MessageUtil.format("json2JsonNode error", e, "json", json));
            return null;
        }
    }

    public static boolean isEmpty(JsonNode jsonNode) {
        if (ObjectUtil.isNull(jsonNode)) {
            return Boolean.TRUE;
        }
        return jsonNode.isEmpty();
    }

    public static <T> T json2Object(String json, TypeReference<T> typeReference) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            log.error(MessageUtil.format("json2Object error", e, "json", json));
            return null;
        }
    }

    @SuppressWarnings(ClassUtil.UNCHECKED)
    public static <T> T json2Object(String json, Class<T> valueType) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }

        if (Objects.equals(valueType, String.class)) {
            return (T) json;
        }

        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (Exception e) {
            log.error(MessageUtil.format("json2Object error", e, "json", json));
            return null;
        }
    }

    public static <T> T json2Object(String json, Method method) {
        return json2Object(json, getJavaType(method.getGenericReturnType()));
    }

    private static <T> T json2Object(String json, JavaType javaType) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            log.error(MessageUtil.format("json2Object error", e, "json", json));
            return null;
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        if (Objects.isNull(fromValue) || Objects.isNull(toValueType)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            log.error(MessageUtil.format("convertValue error", e, "fromValue", fromValue));
            return null;
        }
    }

    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        if (Objects.isNull(fromValue) || Objects.isNull(toValueTypeRef)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(fromValue, toValueTypeRef);
        } catch (Exception e) {
            log.error(MessageUtil.format("convertValue error", e, "fromValue", fromValue));
            return null;
        }
    }

    private static JavaType getJavaType(Type type) {
        if (type instanceof ParameterizedType) {
            // 泛型
            Class<?> rawType = (Class<?>) ((ParameterizedType) type).getRawType();
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();

            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                // 泛型也可能带有泛型, 递归获取
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }
            return TypeFactory.defaultInstance().constructParametricType(rawType, javaTypes);
        } else {
            // 简单类型
            Class<?> rawType = (Class<?>) type;
            return TypeFactory.defaultInstance().constructParametricType(rawType, new JavaType[]{});
        }
    }
}
