package com.scy.core;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;

/**
 * RandomUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/31.
 */
public class RandomUtil {

    private RandomUtil() {
    }

    public static final Random RANDOM = new Random();

    public static final List<Character> RANDOM_CHARS = CollectionUtil.unmodifiableList(CollectionUtil.newArrayList(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    ));

    public static int nextInt(int startInclusive, int endExclusive) {
        return RandomUtils.nextInt(startInclusive, endExclusive);
    }

    public static long nextLong(long startInclusive, long endExclusive) {
        return RandomUtils.nextLong(startInclusive, endExclusive);
    }

    public static boolean nextBoolean() {
        return RandomUtils.nextBoolean();
    }

    public static String getRandomText(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(RANDOM_CHARS.get(nextInt(0, RANDOM_CHARS.size())));
        }
        return sb.toString();
    }
}
