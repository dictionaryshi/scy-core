package com.scy.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import com.scy.core.format.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Objects;
import java.util.TimeZone;

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

    private static final ReadMapper READ_MAPPER = new ReadMapper();

    private static final WriteMapper WRITE_MAPPER = new WriteMapper();

    public static class ReadMapper extends ObjectMapper {

        private static final ObjectMapper READ_MAPPER = getBaseObjectMapper();

        static {
            READ_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        }

        public ReadMapper() {
            super(READ_MAPPER);
        }
    }

    public static class WriteMapper extends ObjectMapper {

        private static final ObjectMapper WRITE_MAPPER = getBaseObjectMapper();

        public WriteMapper() {
            super(WRITE_MAPPER);
        }
    }

    public static ObjectMapper getBaseObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_SECOND));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setTimeZone(SystemUtil.TIME_ZONE_SHANG_HAI);
        return mapper;
    }

    public static String object2Json(Object object) {
        if (Objects.isNull(object)) {
            return StringUtil.EMPTY;
        }

        try {
            return WRITE_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            return StringUtil.EMPTY;
        }
    }
}
