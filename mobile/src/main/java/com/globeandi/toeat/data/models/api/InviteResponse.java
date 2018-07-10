package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class InviteResponse {

    @Expose
    @SerializedName("invites")
    private List<Invite> data;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<Invite> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    /*
        Manage the data that come from the response
        A static nested class is just a nested class that
        doesn't require an instance of its enclosing class.
         */
   public static class Invite {

        @Expose
        private Long id;

        @Expose
        @SerializedName("group_id")
        private Long groupId;

        @Expose
        @SerializedName("user_id")
        private Long userId;

        @Expose
        @SerializedName("group_name")
        private String groupName;

        @Expose
        @SerializedName("created_at")
        private String createdAt;

        public Long getId() {
            return id;
        }

        public Long getUserId() {
            return userId;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Long getGroupId() {
            return groupId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Invite invite = (Invite) o;
            return Objects.equals(id, invite.id) &&
                    Objects.equals(groupId, invite.groupId) &&
                    Objects.equals(userId, invite.userId) &&
                    Objects.equals(groupName, invite.groupName) &&
                    Objects.equals(createdAt, invite.createdAt);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, groupId, userId, groupName, createdAt);
        }
    }
}
