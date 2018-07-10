package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingTripsResponse {

    @Expose
    @SerializedName("upcoming_trips")
    private List<TripResponse.Trip> upcomingTrips;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<TripResponse.Trip> getUpcomingTrips() {
        return upcomingTrips;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
