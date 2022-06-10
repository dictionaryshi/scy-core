package com.scy.core;

import com.scy.core.format.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * RuntimeUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/24.
 */
@Slf4j
public class RuntimeUtil {

    private RuntimeUtil() {
    }

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static int exec(String command, String scriptFile, String logFile, String... params) {
        Process process = null;
        try (FileOutputStream fileOutputStream = new FileOutputStream(logFile, Boolean.TRUE)) {
            List<String> cmdList = new ArrayList<>();
            cmdList.add(command);
            cmdList.add(scriptFile);
            if (!ArrayUtil.isEmpty(params)) {
                Collections.addAll(cmdList, params);
            }

            String[] cmdarray = cmdList.toArray(new String[]{});

            process = Runtime.getRuntime().exec(cmdarray);

            if (Objects.nonNull(process.getInputStream())) {
                IOUtil.copyLarge(process.getInputStream(), fileOutputStream, new byte[IOUtil.DEFAULT_BUFFER_SIZE]);
            }

            if (Objects.nonNull(process.getErrorStream())) {
                IOUtil.copyLarge(process.getErrorStream(), fileOutputStream, new byte[IOUtil.DEFAULT_BUFFER_SIZE]);
            }

            return process.waitFor();
        } catch (Exception e) {
            log.error(MessageUtil.format("java.lang.Runtime.exec error", e));
            return -1;
        } finally {
            if (Objects.nonNull(process)) {
                if (Objects.nonNull(process.getInputStream())) {
                    try {
                        process.getInputStream().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (Objects.nonNull(process.getErrorStream())) {
                    try {
                        process.getErrorStream().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
