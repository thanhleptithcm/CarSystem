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
        public static final String ALL_BOOKING = API + "booking";
        public static final String CONFIRM_BOOKING = API + "booking/{bookId}";
    }

    public final static class Manage {
        public static final String DRIVERS = API + "drivers";

        public static final String PRICE = API + "price";

        public static final String UPDATE_STATUS_DRIVER = API + "drivers/{driverId}";
    }

    public final static class User {
        public static final String DRIVERS_LOCATION = API + "driverLocation";
        public static final String BOOKING = API + "booking";
        public static final String CANCEL_BOOKING = API + "booking/{idBooking}";
    }
}
