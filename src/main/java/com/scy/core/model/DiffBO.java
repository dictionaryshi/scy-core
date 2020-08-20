package com.scy.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DiffBO
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/20.
 */
@Getter
@Setter
@ToString
public class DiffBO {

    private String property;

    private Object before;

    private Object after;
}
