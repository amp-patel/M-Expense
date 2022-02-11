//Reference https://programmerworld.co/android/how-to-store-images-in-sqlite-database-insert-update-delete-and-fetch-in-your-android-app/
package com.example.m_expense.Util;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class Utils {

    // convert bitmap to bytearray to store into database
    public static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    //get image but still pending this feature
    public static Bitmap getImage(byte[] data)
    {
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }
}
