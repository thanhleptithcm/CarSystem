package com.matas.caroperatingsystem.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static boolean isEmailValid(CharSequence email) {
        return email != null && isEmailValid(email.toString());
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        boolean isValid = false;
        String patternFormat = "[A-Za-z0-9\\._%\\-']+@([\\w\\-]+\\.)+[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(patternFormat, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public static String base64decode(String text) {
        // Sending side
        try {
            byte[] data = text.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
