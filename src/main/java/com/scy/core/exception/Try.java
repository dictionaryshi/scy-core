package com.scy.core.exception;

import com.scy.core.format.MessageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author : shichunyang
 * Date    : 2021/11/16
 * Time    : 1:46 下午
 * ---------------------------------------
 * Desc    : Try Catch
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Try {

    public static <T> T of(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            log.error(MessageUtil.format("Try Catch", e));
            return null;
        }
    }
}
