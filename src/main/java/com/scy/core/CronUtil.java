package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

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

    public static Date nextExecuteTime(String cron) {
        return nextExecuteTime(cron, new Date());
    }

    public static Date nextExecuteTime(String cron, Date startTime) {
        Date nextExecuteTime;
        try {
            CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron, SystemUtil.TIME_ZONE_SHANG_HAI);
            nextExecuteTime = cronSequenceGenerator.next(startTime);
        } catch (Throwable throwable) {
            log.error(MessageUtil.format("nextExecuteTime error", throwable, "cron", cron, "startTime", startTime));
            return null;
        }

        if (ObjectUtil.isNull(nextExecuteTime)) {
            return null;
        }

        if (nextExecuteTime.before(startTime)) {
            return null;
        }

        return nextExecuteTime;
    }
}
