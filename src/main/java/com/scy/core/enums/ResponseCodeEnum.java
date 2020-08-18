package com.scy.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResponseCodeEnum
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    /**
     * 响应码
     */
    SUCCESS(0, "success"),
    SYSTEM_EXCEPTION(500, "系统异常"),
    BUSINESS_EXCEPTION(999, "业务异常"),
    PARAMS_EXCEPTION(4000, "参数校验异常"),
    LOGIN_EXCEPTION(4100, "登陆校验异常"),
    SIGN_EXCEPTION(4200, "签名校验异常");

    private final int code;

    private final String message;
}
