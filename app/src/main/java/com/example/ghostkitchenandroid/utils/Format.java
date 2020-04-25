package com.example.ghostkitchenandroid.utils;

import android.telecom.Call;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public abstract class Format {

    public static String phone(String phone) {
        String formattedPhone = "";
        String phoneWithReplacements = phone.replace("[()- ]", "");
        if (phoneWithReplacements.length() == 10)
            formattedPhone = "(" + phoneWithReplacements.substring(0, 3) + ")" + "-" + phoneWithReplacements.substring(3, 6) + "-" + phoneWithReplacements.substring(6, 10);
        else if (phoneWithReplacements.length() == 11)
            formattedPhone = phoneWithReplacements.charAt(0) + "-(" + phoneWithReplacements.substring(1, 4) + ")" + "-" + phoneWithReplacements.substring(4, 7) + "-" + phoneWithReplacements.substring(7, 11);
        return formattedPhone;
    }

    public static String getFormattedTotal(int quantity, double price) {
        return String.format(Locale.US, "$%4.2f", quantity * price);
    }

    public static String getFormattedPrice(double price) {
        return String.format(Locale.US, "$%4.2f", price);
    }

    public static String makeDisplayTimeFromMillis(long millis) {
        Date date = new Date(millis);

        String hourString = "";
        int hour = date.getHours() % 12;
        if (hour == 0)
            hourString = "12";
        else if (hour < 10)
            hourString = "0" + hour;
        else
            hourString = "" + (hour + 1);

        String minuteString = "";
        if (date.getMinutes() < 10)
            minuteString = "0" + date.getMinutes();
        else
            minuteString = "" + date.getMinutes();

        String postfix = "";
        if (date.getHours() >= 12)
            postfix += "PM";
        else
            postfix += "AM";

        return hourString + ":" + minuteString + " " + postfix;
    }

    public static String makeShortDateDisplay(long millis) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(millis);

        return (gregorianCalendar.get(Calendar.MONTH) + 1) + "-" + gregorianCalendar.get(Calendar.DATE) + "-" + gregorianCalendar.get(Calendar.YEAR);
    }
}
