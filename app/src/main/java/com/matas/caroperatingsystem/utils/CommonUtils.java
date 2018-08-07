package com.matas.caroperatingsystem.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;

import com.matas.caroperatingsystem.data.AppStorage;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class CommonUtils {
    @SuppressLint("TrustAllX509TrustManager")
    public static void trustSsl(OkHttpClient.Builder okHttpClientBuilder) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //            0 -> 6: 15000
//            6 -> 9: 10000
//            9 -> 12: 8000
//            12 -> 13: 10000
//            13 -> 17: 8000
//            17 -> 20: 12000
//            20 -> 24: 10000
    public static Double getPrice() {
        int currentHour = DateTimeUtils.getCurrentHour();
        List<Double> listDefaultPrice = AppStorage.getDefaultPricesList();
        switch (currentHour) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return listDefaultPrice.get(0);
            case 6:
            case 7:
            case 8:
                return listDefaultPrice.get(1);
            case 9:
            case 10:
            case 11:
                return listDefaultPrice.get(2);
            case 12:
                return listDefaultPrice.get(3);
            case 13:
            case 14:
            case 15:
            case 16:
                return listDefaultPrice.get(4);
            case 17:
            case 18:
            case 19:
                return listDefaultPrice.get(5);
            case 20:
            case 21:
            case 22:
            case 23:
                return listDefaultPrice.get(6);
        }
        return null;
    }
}
