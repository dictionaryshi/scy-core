package com.scy.core.thread;

import lombok.Getter;

import java.util.concurrent.Callable;

/**
 * AbstractBooleanCallable
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/23.
 */
@Getter
public abstract class AbstractBooleanCallable implements Callable<Boolean> {

    private final String taskName;

    public AbstractBooleanCallable(String taskName) {
        this.taskName = taskName;
    }
}
