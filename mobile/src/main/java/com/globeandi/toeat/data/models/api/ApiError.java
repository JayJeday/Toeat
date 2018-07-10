package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
    manage api error
 */
public class ApiError {

    private int errorCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;


    @Expose
    @SerializedName("errors")
    private Map<String, List<String>> errors;

    public ApiError(int errorCode, String message, String statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return errorCode == apiError.errorCode &&
                Objects.equals(message, apiError.message) &&
                Objects.equals(statusCode, apiError.statusCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(errorCode, message, statusCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
