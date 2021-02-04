package com.scy.core;

import com.google.common.collect.Lists;
import com.scy.core.format.DateUtil;
import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CronUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/27.
 */
@Slf4j
public class CronUtil {

    private CronUtil() {

    }

    public static LocalDateTime nextExecuteTime(String cron) {
        return nextExecuteTime(cron, DateUtil.nowLocalDateTime());
    }

    public static LocalDateTime nextExecuteTime(String cron, LocalDateTime startTime) {
        LocalDateTime nextExecuteTime;
        try {
            CronExpression cronExpression = CronExpression.parse(cron);
            nextExecuteTime = cronExpression.next(startTime);
        } catch (Throwable throwable) {
            log.error(MessageUtil.format("nextExecuteTime error", throwable, "cron", cron, "startTime", startTime));
            return null;
        }

        if (ObjectUtil.isNull(nextExecuteTime)) {
            return null;
        }

        if (nextExecuteTime.isBefore(startTime)) {
            return null;
        }

        return nextExecuteTime;
    }

    public static List<LocalDateTime> nextExecuteTime(String cron, LocalDateTime startTime, int times) {
        List<LocalDateTime> nextExecuteTimes = Lists.newArrayList();
        for (int i = 0; i < times; i++) {
            LocalDateTime nextExecuteTime = nextExecuteTime(cron, startTime);
            if (ObjectUtil.isNull(nextExecuteTime)) {
                return nextExecuteTimes;
            }
            nextExecuteTimes.add(nextExecuteTime);
            startTime = nextExecuteTime;
        }
        return nextExecuteTimes;
    }
}
