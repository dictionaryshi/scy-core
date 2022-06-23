package com.scy.core.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : shichunyang
 * Date    : 2022/6/22
 * Time    : 11:54 上午
 * ---------------------------------------
 * Desc    : 执行单元
 */
@Getter
@Setter
@ToString
public class Unit {

    private String id;

    private int priority;

    private String desc;

    private String className;

    private String methodName;

    private String[] parameterTypes;

    private int retry = 0;

    private boolean status;

    private int code = -1;

    private Throwable throwable;
}
