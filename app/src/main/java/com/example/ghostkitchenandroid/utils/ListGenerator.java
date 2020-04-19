package com.example.ghostkitchenandroid.utils;

import java.util.ArrayList;
import java.util.Date;

public abstract class ListGenerator {

    public static ArrayList<String> makePickupTimes(long fromTimeInMillis) {
        ArrayList<String> pickupTimes = new ArrayList<>(20);
//        deliveryTimes = new ArrayList<>(20);

        Date date = new Date(900000 - (fromTimeInMillis % 900000) + fromTimeInMillis);
        Date pickupDate = new Date(date.getTime());
        Date deliveryDate = new Date(date.getTime() + 900000 * 2);

        for (int i = 1; i <= 20; i++) {
            pickupDate.setTime(pickupDate.getTime() + 900000);
            deliveryDate.setTime(deliveryDate.getTime() + 900000);

            String pickupHourString = "";
            int pickupHour = pickupDate.getHours() % 12;
            if (pickupHour == 0)
                pickupHourString = "12";
            else if (pickupHour < 10)
                pickupHourString = "0" + pickupHour;
            else
                pickupHourString = "" + (pickupHour + 1);

            String pickupMinuteString = "";
            if (pickupDate.getMinutes() < 10)
                pickupMinuteString = "0" + pickupDate.getMinutes();
            else
                pickupMinuteString = "" + pickupDate.getMinutes();

//            String deliveryHour = ((deliveryDate.getHours() % 12) + 1 < 10) ? "0" + (deliveryDate.getHours() % 12) + 1 : "" + (deliveryDate.getHours() % 12) + 1;
            pickupTimes.add(pickupHourString + ":" + pickupMinuteString);
//            deliveryTimes.add(deliveryHour + ":" + deliveryDate.getMinutes());
        }

        return pickupTimes;
    }
}
