package com.clear.html;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chengweisen on 2015/1/6.
 */
public final class ClockUtils {

    private final static Logger logger = LoggerFactory.getLogger(ClockUtils.class);

    public static String getCron() {
        StringBuilder stringBuilder = new StringBuilder();
        int secends = RandomUtils.nextInt(59);
        int mulitis = getMuliDay() + RandomUtils.nextInt(8);
        stringBuilder.append(secends).append(" ");
        stringBuilder.append(mulitis).append(" ");
        stringBuilder.append("8,");
        stringBuilder.append(getDayOfWeek());
        stringBuilder.append(" * * ?");
        return stringBuilder.toString();
    }

    public static String getTestCron() {
        StringBuilder stringBuilder = new StringBuilder();
        int secends = 1 + RandomUtils.nextInt(5);
        stringBuilder.append("0/" + secends).append(" * ");
        stringBuilder.append("*").append(" ");
        stringBuilder.append("* * ?");
        return stringBuilder.toString();
    }

    /**
     * 返回周二或周三执行的时
     *
     * @return
     */
    public static Integer getDayOfWeek() {
        DateTime now = DateTime.now();
        int weeks = 18;
        int i = now.dayOfWeek().get();
        if (i == 2 || i == 4) {
            weeks = 19;
        }
        return weeks;
    }

    /**
     * 返回周二或周三执行的分
     *
     * @return
     */
    public static Integer getMuliDay() {
        DateTime now = DateTime.now();
        int muli = 5;
        int i = now.dayOfWeek().get();
        if (i == 2 || i == 4) {
            muli = 25;
        }
        return muli;
    }

    public static void main(String[] args) {
        System.out.println(getDayOfWeek());
    }

}
