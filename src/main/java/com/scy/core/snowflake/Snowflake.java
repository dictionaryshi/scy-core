package com.scy.core.snowflake;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

/**
 * Snowflake
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/23.
 */
@Slf4j
public class Snowflake {

    private final long twepoch = 1598112000000L;

    private final long sequenceBits = 12L;

    /**
     * 4095
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long workerIdBits = 10L;

    /**
     * 1023
     */
    private final long maxWorkerId = ~(-1L << workerIdBits);

    private final long workerIdShift = sequenceBits;

    private final long timestampLeftShift = sequenceBits + workerIdBits;

    private final long workerId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public Snowflake(long workerId) {
        Preconditions.checkArgument(workerId >= 0 && workerId <= maxWorkerId, "workerID must gte 0 and lte 1023");
        this.workerId = workerId;
    }

    public synchronized Long generate() {
        long timestamp = now();
        if (timestamp < lastTimestamp) {
            log.error("snowflake generate error time delay");
            return null;
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;

    }

    private long nextMillis(long lastTimestamp) {
        long timestamp = now();
        while (timestamp <= lastTimestamp) {
            timestamp = now();
        }
        return timestamp;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    public long getWorkerId() {
        return workerId;
    }
}
