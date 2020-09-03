package com.scy.core.format;

import com.scy.core.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * NumberUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
public class NumberUtil {

    private NumberUtil() {
    }

    public static final Number ZERO = 0;

    /**
     * 向上取整
     */
    public static double ceil(double number) {
        return Math.ceil(number);
    }

    /**
     * 向下取整
     */
    public static double floor(double number) {
        return Math.floor(number);
    }

    /**
     * 四舍五入
     */
    public static long round(double number) {
        return Math.round(number);
    }

    public static BigDecimal add(Number add, Number... numbers) {
        add = null2Zero(add);
        BigDecimal result = new BigDecimal(add.toString());
        for (Number number : numbers) {
            number = null2Zero(number);
            result = result.add(new BigDecimal(number.toString()));
        }
        return result;
    }

    public static BigDecimal subtract(Number subtract, Number... numbers) {
        subtract = null2Zero(subtract);
        BigDecimal result = new BigDecimal(subtract.toString());
        for (Number number : numbers) {
            number = null2Zero(number);
            result = result.subtract(new BigDecimal(number.toString()));
        }
        return result;
    }

    public static BigDecimal multiply(Number multiply, Number... numbers) {
        multiply = null2Zero(multiply);
        if (ObjectUtil.equals(ZERO.doubleValue(), multiply.doubleValue())) {
            return new BigDecimal(ZERO.toString());
        }
        BigDecimal result = new BigDecimal(multiply.toString());
        for (Number number : numbers) {
            number = null2Zero(number);
            if (ObjectUtil.equals(ZERO.doubleValue(), number.doubleValue())) {
                return new BigDecimal(ZERO.toString());
            }
            result = result.multiply(new BigDecimal(number.toString()));
        }
        return result;
    }

    public static BigDecimal divide(Number divide, int decimals, Number... numbers) {
        divide = null2Zero(divide);
        if (ObjectUtil.equals(ZERO.doubleValue(), divide.doubleValue())) {
            return new BigDecimal(ZERO.toString());
        }
        BigDecimal result = new BigDecimal(divide.toString());
        for (Number number : numbers) {
            number = null2Zero(number);
            if (ObjectUtil.equals(ZERO.doubleValue(), number.doubleValue())) {
                return new BigDecimal(ZERO.toString());
            }
            result = result.divide(new BigDecimal(number.toString()), decimals, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    public static BigInteger[] divideAndRemainder(Number divide, Number number) {
        divide = null2Zero(divide);
        number = null2Zero(number);
        if (ObjectUtil.equals(ZERO.doubleValue(), divide.doubleValue())) {
            return new BigInteger[]{new BigInteger(ZERO.toString()), new BigInteger(ZERO.toString())};
        }
        if (ObjectUtil.equals(ZERO.doubleValue(), number.doubleValue())) {
            return new BigInteger[]{new BigInteger(ZERO.toString()), new BigInteger(ZERO.toString())};
        }
        return new BigInteger(divide.toString()).divideAndRemainder(new BigInteger(number.toString()));
    }

    public static String percentage(Number divide, Number number, int decimals, int percentage) {
        BigDecimal divideResult = divide(divide, decimals, number);
        BigDecimal multiplyResult = multiply(divideResult, 100);
        return divide(multiplyResult, percentage, 1).toString() + "%";
    }

    public static Number null2Zero(Number number) {
        if (ObjectUtil.isNull(number)) {
            return ZERO;
        }
        return number;
    }
}
