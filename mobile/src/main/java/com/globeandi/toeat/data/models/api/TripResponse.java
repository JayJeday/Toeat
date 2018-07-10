package com.globeandi.toeat.data.models.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/*
Trip response from Google Api
 */
public class TripResponse {

    @Expose
    @SerializedName("data")
    private TripHolder tripInfo;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("is_empty")
    private Boolean isTripEmpty;

    public Boolean getTripEmpty() {
        return isTripEmpty;
    }

    public void setTripEmpty(Boolean tripEmpty) {
        isTripEmpty = tripEmpty;
    }

    public TripHolder getTripInfo() {
        return tripInfo;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }


    /*
    class that represent the whole trip data
     */
    public static class TripHolder{

        @Expose
        @SerializedName("trip")
        private Trip trip;

        @Expose
        @SerializedName("outings")
        private List<Outing> mOutings;


        public Trip getTrip() {
            return trip;
        }

        public List<Outing> getOutings() {
            return mOutings;
        }
    }
    /*
        A static nested class is just a nested class that doesn't require
         an instance of its enclosing class.
         */
    public static class Trip {

        @Expose
        private Long id;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("places")
        private List<PlacesToEat> mPlacesToEats;

        @Expose
        @SerializedName("is_completed")
        private Boolean isCompleted;

        @Expose
        @SerializedName("trip_date_time")
        private String tripDateTime;

        @Expose
        @SerializedName("trip_date")
        private String tripDate;

        @Expose
        @SerializedName("trip_time")
        private String tripTime;

        @Expose
        @SerializedName("is_voting")
        private Boolean isVoting;

        @Expose
        @SerializedName("is_carpool")
        private Boolean isCarpool;

        @Expose
        @SerializedName("is_active")
        private Boolean isActive;

        @Expose
        @SerializedName("created_at")
        private String createdAt;

        @Expose
        @SerializedName("updated_at")
        private String updatedAt;

        @Expose
        @SerializedName("outings_count")
        private Integer numUsers;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trip trip = (Trip) o;
            return numUsers == trip.numUsers &&
                    Objects.equals(id, trip.id) &&
                    Objects.equals(title, trip.title) &&
                    Objects.equals(mPlacesToEats, trip.mPlacesToEats) &&
                    Objects.equals(isCompleted, trip.isCompleted) &&
                    Objects.equals(tripDateTime, trip.tripDateTime) &&
                    Objects.equals(tripDate, trip.tripDate) &&
                    Objects.equals(tripTime, trip.tripTime) &&
                    Objects.equals(isVoting, trip.isVoting) &&
                    Objects.equals(isCarpool, trip.isCarpool) &&
                    Objects.equals(isActive, trip.isActive) &&
                    Objects.equals(createdAt, trip.createdAt) &&
                    Objects.equals(updatedAt, trip.updatedAt);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, title, mPlacesToEats, isCompleted, tripDateTime, tripDate, tripTime, isVoting, isCarpool, isActive, createdAt, updatedAt, numUsers);
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public String getTripDateTime() {
            return tripDateTime;
        }

        public String getTripTime() {
            return tripTime;
        }

        public Boolean getVoting() {
            return isVoting;
        }

        public Boolean getCarpool() {
            return isCarpool;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Boolean getActive() {
            return isActive;
        }

        public String getTripDate() {
            return tripDate;
        }

        public void setActive(Boolean active) {
            isActive = active;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCompleted(Boolean completed) {
            isCompleted = completed;
        }

        public void setTripDate(String tripDate) {
            this.tripDate = tripDate;
        }

        public void setTripDateTime(String tripDateTime) {
            this.tripDateTime = tripDateTime;
        }

        public void setTripTime(String tripTime) {
            this.tripTime = tripTime;
        }

        public void setVoting(Boolean voting) {
            isVoting = voting;
        }

        public void setCarpool(Boolean carpool) {
            isCarpool = carpool;
        }

        public Integer getNumUsers() {
            return numUsers;
        }

        public void setNumUsers(Integer numUsers) {
            this.numUsers = numUsers;
        }

        public List<PlacesToEat> getPlacesToEats() {
            return mPlacesToEats;
        }

        public void setPlacesToEats(List<PlacesToEat> placesToEats) {
            mPlacesToEats = placesToEats;
        }
    }

    /*
        Places for my api
     */
    public static class PlacesToEat {

        @Expose
        private Long id;

        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("vicinity")
        private String vicinity;

        @Expose
        @SerializedName("latitude")
        private String latitude;

        @Expose
        @SerializedName("longitude")
        private String longitude;


        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlacesToEat that = (PlacesToEat) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(vicinity, that.vicinity) &&
                    Objects.equals(latitude, that.latitude) &&
                    Objects.equals(longitude, that.longitude);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, name, vicinity, latitude, longitude);
        }
    }

    public static class Outing{

        @Expose
        private Long id;

        @Expose
        @SerializedName("voting_place")
        private Long votingPlace;

        @Expose
        @SerializedName("user_id")
        private Long userId;

        //get user going info
        @Expose
        @SerializedName("user")
        private SearchResponse.User UserGoing;

        @Expose
        @SerializedName("voted")
        private Boolean voted;

        public Boolean getVoted() {
            return voted;
        }

        public void setVoted(Boolean voted) {
            this.voted = voted;
        }

        public Long getVotingPlace() {
            return votingPlace;
        }

        public SearchResponse.User getUserGoing() {
            return UserGoing;
        }

        public Long getUserId() {
            return userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Outing outing = (Outing) o;
            return Objects.equals(id, outing.id) &&
                    Objects.equals(votingPlace, outing.votingPlace) &&
                    Objects.equals(userId, outing.userId) &&
                    Objects.equals(UserGoing, outing.UserGoing) &&
                    Objects.equals(voted, outing.voted);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, votingPlace, userId, UserGoing, voted);
        }
    }

}
