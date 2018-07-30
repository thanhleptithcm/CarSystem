package com.matas.caroperatingsystem.data.network;

public class Urls {
    // Request headers
    public static final String REQUEST_HEADER_TOKEN = "access-token";
    public static final String API = "api/";

    public final static class Auth {
        public static final String LOGIN = API + "login";
        public static final String SIGNUP = API + "signup";
    }

    public final static class Staff {
        public static final String USER = API + "user";
        public static final String STATUS_BIKER = API + "driverLocation/status";
        public static final String DRIVERS_LOCATION = API + "driverLocation";
    }

    public final static class Manage {
        public static final String DRIVERS = API + "drivers";
    }

    public final static class User {
        public static final String DRIVERS_LOCATION = API + "driverLocation";
        public static final String BOOKING = API + "booking";
    }
}
