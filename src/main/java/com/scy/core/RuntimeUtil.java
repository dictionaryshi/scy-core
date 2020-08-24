package com.scy.core;

/**
 * RuntimeUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/24.
 */
public class RuntimeUtil {

    private RuntimeUtil() {
    }

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void addShutdownHook(Thread hook) {
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
