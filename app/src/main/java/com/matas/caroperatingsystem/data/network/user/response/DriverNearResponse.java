package com.matas.caroperatingsystem.data.network.user.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matas.caroperatingsystem.data.model.Driver;
import com.matas.caroperatingsystem.data.model.PosLocation;
import com.matas.caroperatingsystem.data.model.User;
import com.matas.caroperatingsystem.data.network.BaseResponse;

import java.util.List;

public class DriverNearResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Data> driverNears;

    public List<Data> getDriverNears() {
        return driverNears;
    }

    public class Data{

        @SerializedName("location")
        @Expose
        private PosLocation location;

        @SerializedName("active")
        @Expose
        private boolean active;

        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("socketId")
        @Expose
        private String socketId;

        @SerializedName("driverId")
        @Expose
        private User driver;

        public PosLocation getLocation() {
            return location;
        }

        public void setLocation(PosLocation location) {
            this.location = location;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSocketId() {
            return socketId;
        }

        public void setSocketId(String socketId) {
            this.socketId = socketId;
        }

        public User getDriver() {
            return driver;
        }

        public void setDriver(User driver) {
            this.driver = driver;
        }
    }
}
