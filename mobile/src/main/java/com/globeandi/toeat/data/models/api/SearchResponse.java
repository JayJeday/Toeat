package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class SearchResponse {

    @Expose
    @SerializedName("users")
    private List<User> mUsers;

    @Expose
    @SerializedName("names")
    private List<User> mNames;

    @Expose
    @SerializedName("invites")
    private List<InviteResponse.Invite> invitesGroupList;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<User> getUsers() {
        return mUsers;
    }

    //this method is for the search automatically
    public List<User> getNames() {
        return mNames;
    }

    //invite get group list
    public List<InviteResponse.Invite> getInvitesGroupList() {
        return invitesGroupList;
    }

    public static class User {

        @Expose
        private Long id;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("profile_pic")
        private String profile_picture;

        private boolean isInvited;

        public boolean isInvited() {
            return isInvited;
        }

        public void setInvited(boolean invited) {
            isInvited = invited;
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getProfile_picture() {
            return profile_picture;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) &&
                    Objects.equals(email, user.email) &&
                    Objects.equals(name, user.name) &&
                    Objects.equals(profile_picture, user.profile_picture);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, email, name, profile_picture);
        }
    }


}
