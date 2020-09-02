package com.scy.core.rest;

import com.scy.core.ObjectUtil;
import com.scy.core.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * ResponseResult
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/21.
 */
@ApiModel("响应结果")
@NoArgsConstructor
@Getter
@ToString
public class ResponseResult<T> implements Serializable {

    @ApiModelProperty(value = "响应状态码(0:成功, 其它:失败)", required = true, example = "0")
    private int code;

    @ApiModelProperty(value = "响应状态", required = true, example = "true")
    private boolean success;

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    private ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = ObjectUtil.equals(code, ResponseCodeEnum.SUCCESS.getCode());
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResponseCodeEnum.SUCCESS.getCode(), null, data);
    }

    public static <T> ResponseResult<T> error(int code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }
}
