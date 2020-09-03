package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * BeanCopyUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
@Slf4j
public class BeanCopyUtil {

    private BeanCopyUtil() {
    }

    public static <T> T copyBean(Object sourceObject, Class<T> clazz) {
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(sourceObject, target);
            return target;
        } catch (Exception e) {
            log.error(MessageUtil.format("copyBean error", e, "sourceObject", sourceObject));
            return null;
        }
    }
}
