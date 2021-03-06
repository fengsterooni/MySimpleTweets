package com.codepath.apps.mysimpletweets.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    static Calendar cal = Calendar.getInstance();

    public static String getDateString(Date date){
        return (new SimpleDateFormat("MMM dd, yyyy", Locale.US)).format(date);
    }

    public static String getDayString(Date date){
        return (new SimpleDateFormat("dd", Locale.US)).format(date);
    }

    public static String getMonthString(Date date){
        return (new SimpleDateFormat("MMMM", Locale.US)).format(date);
    }

    public static String getShortMonthString(Date date){
        return (new SimpleDateFormat("MMM", Locale.US)).format(date);
    }

    public static String getYearString(Date date){
        return (new SimpleDateFormat("yyyy", Locale.US)).format(date);
    }

    public static String getShortDateTimeString(Date date){
        return (new SimpleDateFormat("h:mm aaa - dd MMM yy", Locale.US)).format(date);
    }

    public static String getTime12(String time){
        Log.i("INFO", "Time string: " + time);
        int newTime = 0;
        String exactTime;
        //time = time.substring(0,2);
        //System.out.println("#######" + time);
        time = time.substring(0, time.indexOf(':')!=-1?time.indexOf(':'):time.length());//IN windows Date comes as 10:00
        if(Integer.parseInt(time) > 12){
            newTime = Integer.parseInt(time.substring(0, 2)) - 12;
            exactTime = Integer.toString(newTime) + ":00 PM";
        } else if (Integer.parseInt(time) == 12){
            exactTime = time + ":00 PM";
        } else {
            exactTime = time + ":00 AM";
        }
        return exactTime;
    }

    public static int getYear(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getDateFromString(String string) {
        Date date = null;
        try {
            date = (new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)).parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long getDateLong(Date date) {
        cal.setTime(date);
        return cal.getTimeInMillis();
    }
}
