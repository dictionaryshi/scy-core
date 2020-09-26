package com.scy.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OrderEnum
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/20.
 */
@Getter
@AllArgsConstructor
public enum OrderEnum {

    /**
     * 切面顺序
     */
    SYSTEM_START(0, "系统入口切面"),
    REQUEST_LIMIT_CHECK(6000, "请求频率校验切面"),
    REQUEST_RESUBMIT(12000, "请求防重复提交切面"),
    CACHE(18000, "缓存切面"),
    ;

    private final int order;

    private final String message;
}
