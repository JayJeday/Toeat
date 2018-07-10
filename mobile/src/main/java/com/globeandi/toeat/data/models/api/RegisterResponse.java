package com.globeandi.toeat.data.models.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class RegisterResponse {

    @Expose
    @SerializedName("response")
    private AuthResponse mAuthResponse;

    @Expose
    @SerializedName("user")
    private SearchResponse.User mUser;

    public AuthResponse getAuthResponse() {
        return mAuthResponse;
    }

    public SearchResponse.User getUser() {
        return mUser;
    }
}
