package com.scy.core;

import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/31.
 */
public class RandomUtil {

    private RandomUtil() {
    }

    public static final List<Character> RANDOM_CHARS = CollectionUtil.unmodifiableList(CollectionUtil.newArrayList(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    ));

    public static int nextInt(int startInclusive, int endExclusive) {
        Validate.isTrue(endExclusive >= startInclusive,
                "Start value must be smaller or equal to end value.");
        Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + ThreadLocalRandom.current().nextInt(endExclusive - startInclusive);
    }

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static String getRandomText(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(RANDOM_CHARS.get(nextInt(0, RANDOM_CHARS.size())));
        }
        return sb.toString();
    }
}
