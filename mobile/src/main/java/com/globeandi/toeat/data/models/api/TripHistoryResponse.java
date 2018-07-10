package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripHistoryResponse {

    @Expose
    @SerializedName("completed_trips")
    private List<TripResponse.Trip> tripsHistory;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<TripResponse.Trip> getTripsHistory() {
        return tripsHistory;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
