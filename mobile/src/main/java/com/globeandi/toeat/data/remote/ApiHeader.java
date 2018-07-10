package com.globeandi.toeat.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;

    @Inject
    public ApiHeader(ProtectedApiHeader protectedApiHeader){
        this.mProtectedApiHeader = protectedApiHeader;
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("access_token")
        private String mAccessToken;

        @Expose
        @SerializedName("refresh_token")
        private String mRefreshToken;

        @Expose
        @SerializedName("token_type")
        private String mTokenType;

        @Expose
        @SerializedName("expire_in")
        private int mExpireIn;

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("user_id")
        private Long mUserId;

        public ProtectedApiHeader(Long mUserId, String mAccessToken) {
            this.mUserId = mUserId;
            this.mAccessToken = mAccessToken;
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public String getRefreshToken() {
            return mRefreshToken;
        }

        public String getTokenType() {
            return mTokenType;
        }

        public int getExpireIn() {
            return mExpireIn;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public Long getUserId() {
            return mUserId;
        }
    }
}
