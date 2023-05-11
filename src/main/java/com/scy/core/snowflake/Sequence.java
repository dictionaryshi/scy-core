package com.scy.core.snowflake;

import com.google.common.base.Preconditions;
import com.scy.core.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : shichunyang
 * Date    : 2023/5/10
 * Time    : 10:19 上午
 * ---------------------------------------
 * Desc    : Sequence
 */
@Slf4j
public class Sequence {

    public static final long TIMESTAMP_FIX = 1_000000000L;

    public static final long BIZ_FIX = 1_000000L;

    public static final long WORKER_ID_FIX = 1_0000L;

    private final long bizMax = 999L;

    private final long sequenceMax = 9999L;

    private final long workerIdMax = 99L;

    private final long workerId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public Sequence(long workerId) {
        Preconditions.checkArgument(workerId >= 0 && workerId <= workerIdMax, "workerID must gte 0 and lte " + workerIdMax);
        this.workerId = workerId;
    }

    public synchronized Long generate(long biz) {
        if (biz < 0 || biz > bizMax) {
            log.error("snowflake biz must gte 0 and lte " + bizMax);
            return null;
        }

        long timestamp = now();
        if (timestamp < lastTimestamp) {
            log.error("snowflake generate error time delay");
            return null;
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            sequence = RandomUtil.nextLong(0, 1000);
        }

        lastTimestamp = timestamp;

        return (timestamp * TIMESTAMP_FIX) + (biz * BIZ_FIX) + (workerId * WORKER_ID_FIX) + sequence;

    }

    private long nextMillis(long lastTimestamp) {
        long timestamp = now();
        while (timestamp <= lastTimestamp) {
            timestamp = now();
        }
        return timestamp;
    }

    private long now() {
        return System.currentTimeMillis() / 1000L;
    }

    public long getWorkerId() {
        return workerId;
    }

    public static long getBiz(long orderId) {
        return (orderId / BIZ_FIX) - ((orderId / TIMESTAMP_FIX) * 1000L);
    }
}
