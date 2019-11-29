package com.example.foodtrackingapp.helperclasses;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {

    //get all forms of time
    private static Calendar calendar = new GregorianCalendar();

    public static int getCurrentYear(){
        int year=calendar.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth(){
        int month=calendar.get(Calendar.MONTH);
        return month;
    }

    public static int getCurrentDay(){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getCurrentHour(){
        int hour = calendar.get(Calendar.HOUR);
        return hour;
    }

    public static int getCurrentMinute(){
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

}
