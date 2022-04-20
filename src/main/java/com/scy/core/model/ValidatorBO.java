package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : shichunyang
 * Date    : 2022/4/20
 * Time    : 8:45 下午
 * ---------------------------------------
 * Desc    : ValidatorBO
 */
@Getter
@Setter
@ToString
public class ValidatorBO {

    private String property;

    private Object value;

    private String message;
}
