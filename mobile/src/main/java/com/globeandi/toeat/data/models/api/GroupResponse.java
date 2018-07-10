package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class GroupResponse {

    @Expose
    @SerializedName("data")
    private List<Group> data;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<Group> getData() {
        return data;
    }

    /*
        Manage the data that come from the response
        A static nested class is just a nested class
        that doesn't require an instance of its enclosing class.
         */
    public static class Group {

        @Expose
        private Long id;

        @Expose
        @SerializedName("group_id")
        private Long groupId;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("is_admin")
        private Boolean isAdmin ;

        @Expose
        @SerializedName("created_at")
        private String createdDated;

        @Expose
        @SerializedName("updated_at")
        private String updatedAt;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Group group = (Group) o;
            return Objects.equals(id, group.id) &&
                    Objects.equals(groupId, group.groupId) &&
                    Objects.equals(name, group.name) &&
                    Objects.equals(description, group.description) &&
                    Objects.equals(isAdmin, group.isAdmin) &&
                    Objects.equals(createdDated, group.createdDated) &&
                    Objects.equals(updatedAt, group.updatedAt);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, groupId, name, description, isAdmin, createdDated, updatedAt);
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreatedDated() {
            return createdDated;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Long getGroupId() {
            return groupId;
        }

        public Boolean getAdmin() {
            return isAdmin;
        }
    }
}
