package com.scy.core.thread;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : shichunyang
 * Date    : 2022/1/30
 * Time    : 9:25 下午
 * ---------------------------------------
 * Desc    : ThreadUtil
 */
@Slf4j
public class ThreadUtil {

    private ThreadUtil() {
    }

    public static void quietSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.info(MessageUtil.format("quietSleep interrupt", e));
        }
    }
}
