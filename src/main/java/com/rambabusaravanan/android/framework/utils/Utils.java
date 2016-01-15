package com.rambabusaravanan.android.framework.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andro Babu on Oct 01, 2015.
 */
public class Utils {

    public static void log(Object object) {
        System.out.println(object);
    }

    public static int daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return (int) TimeUnit.DAYS.convert(Math.abs(end - start), TimeUnit.MILLISECONDS);
    }

    public static <T> boolean checkDuplicate(T ... objects) {
        List<T> list = Arrays.asList(objects);
        Set<T> set = new HashSet(list);
        return list.size() != set.size();
    }

}
