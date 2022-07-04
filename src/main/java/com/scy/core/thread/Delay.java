package com.scy.core.thread;

import lombok.*;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

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
@NoArgsConstructor
@AllArgsConstructor
public class Delay<T> implements Delayed {

    private long delayTime;

    private T data;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }
}
