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
    PAGE_PARAM_CHECK(6000, "分页参数校验切面"),
    REQUEST_LIMIT_CHECK(12000, "请求频率校验切面"),
    REQUEST_RESUBMIT(18000, "请求防重复提交切面"),
    DATA_SOURCE_MASTER_SLAVE(24000, "数据库主从设置切面"),
    CACHE(30000, "缓存切面"),
    ;

    private final int order;

    private final String message;
}
