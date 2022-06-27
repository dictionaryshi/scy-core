package com.scy.core.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author : shichunyang
 * Date    : 2022/6/27
 * Time    : 7:33 下午
 * ---------------------------------------
 * Desc    : Delay
 */
@Getter
@Setter
@ToString
public class Delay<T> implements Delayed {

    private long delayTime;

    private T data;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(NANOSECONDS), o.getDelay(NANOSECONDS));
    }
}
