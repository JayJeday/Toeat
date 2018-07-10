package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AuthResponse {

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("expires_in")
    private int expiresIn;

    @Expose
    @SerializedName("refresh_token")
    private String refreshToken;

    @Expose
    @SerializedName("token_type")
    private String tokenType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return expiresIn == that.expiresIn &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(tokenType, that.tokenType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accessToken, expiresIn, refreshToken, tokenType);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
