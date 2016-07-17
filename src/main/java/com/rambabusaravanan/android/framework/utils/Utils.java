package com.rambabusaravanan.android.framework.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    public static void log(Object... message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : message) {
            stringBuilder.append(obj);
        }
//        System.out.println("ANDRO BABU : " + stringBuilder);
        Log.d("ANDRO BABU : ", stringBuilder.toString());
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int stringResId) {
        Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_SHORT).show();
    }


    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }



    public static int daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return (int) TimeUnit.DAYS.convert(Math.abs(end - start), TimeUnit.MILLISECONDS);
    }

    public static <T> boolean checkDuplicate(T ... objects) {
        List<T> list = Arrays.asList(objects);
        Set<T> set = new HashSet<>(list);
        return list.size() != set.size();
    }

}
