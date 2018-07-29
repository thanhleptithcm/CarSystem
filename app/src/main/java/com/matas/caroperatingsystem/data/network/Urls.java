package com.matas.caroperatingsystem.data.network;

public class Urls {
    // Request headers
    public static final String REQUEST_HEADER_TOKEN = "access-token";

    public final static class Auth {
        public static final String LOGIN = "login";
        public static final String SIGNUP = "signup";
    }

    public final static class Staff {
        public static final String USER = "user";
        public static final String STATUS_BIKER = "driverLocation/status";
    }
}
