package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/*
This class will handle the login different type of request
send request with necessary credentials
 */
public final class LoginRequest {

    private LoginRequest() {

    }

    public static class FacebookLoginRequest {

        @Expose
        @SerializedName("fb_access_token")
        private String fbAccessToken;

        @Expose
        @SerializedName("fb_user_id")
        private String fbUserId;

        public FacebookLoginRequest(String fbUserId, String fbAccessToken) {
            this.fbUserId = fbUserId;
            this.fbAccessToken = fbAccessToken;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FacebookLoginRequest fblr = (FacebookLoginRequest) obj;

            //TODO:: study this
            if (fbUserId != null ? !fbUserId.equals(fblr.fbUserId) : fblr.fbUserId != null) {
                return false;
            }

            return fbAccessToken != null ? fbAccessToken.equals(fblr.fbAccessToken)
                    : fblr.fbAccessToken == null;
        }

        @Override
        public int hashCode() {
            int result = fbUserId != null ? fbUserId.hashCode() : 0;
            result = 31 * result + (fbAccessToken != null ? fbAccessToken.hashCode() : 0);
            return result;
        }

        public String getFbAccessToken() {
            return fbAccessToken;
        }

        public String getFbUserId() {
            return fbUserId;
        }
    }

    public static class GoogleLoginRequest {

        @Expose
        @SerializedName("google_user_id")
        private String googleUserId;

        @Expose
        @SerializedName("google_id_token")
        private String idToken;

        public GoogleLoginRequest(String googleUserId, String idToken) {
            this.googleUserId = googleUserId;
            this.idToken = idToken;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GoogleLoginRequest that = (GoogleLoginRequest) o;
            return Objects.equals(googleUserId, that.googleUserId) &&
                    Objects.equals(idToken, that.idToken);
        }

        @Override
        public int hashCode() {

            return Objects.hash(googleUserId, idToken);
        }

        public String getGoogleUserId() {
            return googleUserId;
        }

        public String getIdToken() {
            return idToken;
        }
    }

    public static class ServerLoginRequest {

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("password")
        private String password;

        public ServerLoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ServerLoginRequest that = (ServerLoginRequest) o;
            return Objects.equals(email, that.email) &&
                    Objects.equals(password, that.password);
        }

        @Override
        public int hashCode() {

            return Objects.hash(email, password);
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
