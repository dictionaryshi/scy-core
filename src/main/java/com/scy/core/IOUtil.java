package com.scy.core;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Collection;
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

    public static boolean deleteQuietly(File file) {
        return FileUtils.deleteQuietly(file);
    }

    public static File getUserDirectory() {
        return FileUtils.getUserDirectory();
    }

    public static File getFile(File directory, String... names) {
        return FileUtils.getFile(directory, names);
    }

    public static File getFile(String... names) {
        return FileUtils.getFile(names);
    }

    public static long lastModified(File file) throws IOException {
        return FileUtils.lastModified(file);
    }

    public static String readFileToString(File file, String charsetName) throws IOException {
        return FileUtils.readFileToString(file, charsetName);
    }

    public static List<String> readLines(File file, String charsetName) throws IOException {
        return trim(FileUtils.readLines(file, charsetName));
    }

    public static void writeStringToFile(File file, String data, String charsetName, boolean append) throws IOException {
        FileUtils.writeStringToFile(file, data, charsetName, append);
    }

    public static void writeLines(File file, String charsetName, Collection<?> lines, boolean append) throws IOException {
        FileUtils.writeLines(file, charsetName, lines, append);
    }
}
