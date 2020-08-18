package com.scy.core.exception;

import com.scy.core.enums.ResponseCodeEnum;
import lombok.Getter;

/**
 * BusinessException
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    private final String message;

    private Throwable throwable;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResponseCodeEnum.BUSINESS_EXCEPTION.getCode();
        this.message = message;
    }

    public BusinessException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = ResponseCodeEnum.SYSTEM_EXCEPTION.getCode();
        this.message = message;
        this.throwable = throwable;
    }

    public static void checkParam(boolean flag, String errorMessage) {
        if (!flag) {
            throw new BusinessException(ResponseCodeEnum.PARAMS_EXCEPTION.getCode(), errorMessage);
        }
    }
}
