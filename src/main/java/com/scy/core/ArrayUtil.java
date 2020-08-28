package com.scy.core;

import org.apache.commons.lang3.ArrayUtils;

/**
 * ArrayUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/28.
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    public static final int[] EMPTY_INT_ARRAY = new int[0];

    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static boolean isArray(Object arr) {
        if (arr == null) {
            return Boolean.FALSE;
        }

        return arr.getClass().isArray();
    }

    public static int getLength(Object arr) {
        if (arr == null) {
            return 0;
        }
        if (!arr.getClass().isArray()) {
            return 0;
        }
        return ArrayUtils.getLength(arr);
    }

    public static boolean isEmpty(Object arr) {
        if (arr == null) {
            return Boolean.TRUE;
        }

        if (!arr.getClass().isArray()) {
            return Boolean.TRUE;
        }

        return ArrayUtils.getLength(arr) == 0;
    }
}
