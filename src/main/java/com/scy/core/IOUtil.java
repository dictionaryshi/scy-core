package com.scy.core;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IOUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/1.
 */
public class IOUtil {

    private IOUtil() {
    }

    public static final int DEFAULT_BUFFER_SIZE = IOUtils.DEFAULT_BUFFER_SIZE;

    public static final char DIR_SEPARATOR = File.separatorChar;

    public static final char DIR_SEPARATOR_UNIX = IOUtils.DIR_SEPARATOR_UNIX;

    public static final char DIR_SEPARATOR_WINDOWS = IOUtils.DIR_SEPARATOR_WINDOWS;

    public static final int EOF = IOUtils.EOF;

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }

    public static byte[] toByteArray(Reader inputStream, String charsetName) throws IOException {
        return IOUtils.toByteArray(inputStream, charsetName);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] buffer) throws IOException {
        return IOUtils.copyLarge(inputStream, outputStream, buffer);
    }

    public static long copyLarge(Reader inputStream, Writer outputStream, char[] buffer) throws IOException {
        return IOUtils.copyLarge(inputStream, outputStream, buffer);
    }

    public static List<String> readLines(InputStream inputStream, String charsetName) throws IOException {
        return trim(IOUtils.readLines(inputStream, charsetName));
    }

    public static List<String> readLines(Reader inputStream) throws IOException {
        return trim(IOUtils.readLines(inputStream));
    }

    private static List<String> trim(List<String> lines) {
        if (CollectionUtil.isEmpty(lines)) {
            return CollectionUtil.emptyList();
        }
        return lines.stream().filter(line -> !StringUtil.isEmpty(line)).map(String::trim).collect(Collectors.toList());
    }
}
