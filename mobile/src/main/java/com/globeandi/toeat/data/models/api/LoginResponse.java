package com.globeandi.toeat.data.models.api;
/*
    get access token in the response
    receive/represent the response from the server
    with the info that I need
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class LoginResponse {

    @Expose
    @SerializedName("user")
    private SearchResponse.User user;

    @Expose
    @SerializedName("response")
    private AuthResponse mAuthResponse;

    @Expose
    @SerializedName("fb_profile_pic_url")
    private String fbProfilePicUrl;

    @Expose
    @SerializedName("google_profile_pic_url")
    private String googleProfilePicUrl;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;


    public String getFbProfilePicUrl() {
        return fbProfilePicUrl;
    }

    public String getGoogleProfilePicUrl() {
        return googleProfilePicUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public SearchResponse.User getUser() {
        return user;
    }

    public AuthResponse getAuthResponse() {
        return mAuthResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(fbProfilePicUrl, that.fbProfilePicUrl) &&
                Objects.equals(googleProfilePicUrl, that.googleProfilePicUrl) &&
                Objects.equals(message, that.message) &&
                Objects.equals(statusCode, that.statusCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fbProfilePicUrl, googleProfilePicUrl, message, statusCode);
    }
}
