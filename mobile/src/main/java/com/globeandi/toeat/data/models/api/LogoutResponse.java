package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LogoutResponse {


    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;


    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutResponse that = (LogoutResponse) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(statusCode, that.statusCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, statusCode);
    }
}
