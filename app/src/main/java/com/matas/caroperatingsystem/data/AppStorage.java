package com.matas.caroperatingsystem.data;

import java.util.ArrayList;
import java.util.List;

public class AppStorage {
    private static List<Double> mDefaultPrices;

//            0 -> 6: 15000
//            6 -> 9: 10000
//            9 -> 12: 8000
//            12 -> 13: 10000
//            13 -> 17: 8000
//            17 -> 20: 12000
//            20 -> 24: 10000
    public static List<Double> getDefaultPricesList() {
        if (mDefaultPrices == null) {
            mDefaultPrices = new ArrayList<>();
            mDefaultPrices.add(15000.00);
            mDefaultPrices.add(10000.00);
            mDefaultPrices.add(8000.00);
            mDefaultPrices.add(10000.00);
            mDefaultPrices.add(8000.00);
            mDefaultPrices.add(12000.00);
            mDefaultPrices.add(10000.00);
        }
        return mDefaultPrices;
    }

    public static void setDefaultPrices(List<Double> mDefaultPrices) {
        AppStorage.mDefaultPrices = mDefaultPrices;
    }
}
