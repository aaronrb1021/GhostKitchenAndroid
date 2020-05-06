package com.example.ghostkitchenandroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ghostkitchenandroid.model.Kitchen;

import java.util.Base64;

public abstract class KitchenDecoder {

    /**
     *
     * @param kitchen
     * @return <code>null</code> if the kitchen base64 string is null
     *         <code>Bitmap</code> if the base64 string in Kitchen is not null
     */
    public static Bitmap base64ToBitmap(Kitchen kitchen) {
        String base64ImageString = kitchen.getImageBytes();

        if (base64ImageString != null) {
            byte[] bytes = Base64.getDecoder().decode(base64ImageString);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }
}
