package com.wzf.baseclasslibrary.utils;

public class NullUtil {
    /**
     * 如果位空则抛出异常
     *
     * @param o 需要判断的对象
     * @param s 抛出异常的说明
     * @return
     */
    public static <T> T nullAndThrow(T o, String s) {
        if (o == null)
            throw new NullPointerException(s + "不可为空");
        return o;
    }

    public static <T> T nullAndThrow(T o) {
        return nullAndThrow(o, "");
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}
