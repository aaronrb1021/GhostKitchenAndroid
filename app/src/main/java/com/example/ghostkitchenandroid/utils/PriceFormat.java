package com.example.ghostkitchenandroid.utils;

import java.util.Locale;

public abstract class PriceFormat {

    public static String getFormattedTotal(int quantity, double price) {
        return String.format(Locale.US, "$%4.2f", quantity * price);
    }
}
