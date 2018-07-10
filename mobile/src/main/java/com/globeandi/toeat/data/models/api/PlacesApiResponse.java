package com.globeandi.toeat.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/*
Places Api Response for when  we SearchActivity for the trip
 */
public class PlacesApiResponse {

    @Expose
    @SerializedName("results")
    private List<Place> mPlaces;

    @Expose
    @SerializedName("html_attributions")
    private List<String> htmlAttr;

    @Expose
    @SerializedName("status")
    private String status;

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public List<String> getHtmlAttr() {
        return htmlAttr;
    }

    public String getStatus() {
        return status;
    }

    public static class Place {

        @SerializedName("id")
        @Expose
        private String id;

        // get latitude and longitude
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;

        //get icon to displayed in the card
        @SerializedName("icon")
        @Expose
        private String icon;

        //get name of the restaurant to displayed in the card
        @SerializedName("name")
        @Expose
        private String name;

        //id that will serve as  query to get detail about the place
        @SerializedName("place_id")
        @Expose
        private String placeId;

        //get rating of the restaurant to displayed in the card
        @SerializedName("rating")
        @Expose
        private Double rating;

        //Address for the place
        @SerializedName("vicinity")
        @Expose
        private String vicinity;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Place place = (Place) o;
            return Objects.equals(geometry, place.geometry) &&
                    Objects.equals(icon, place.icon) &&
                    Objects.equals(id, place.id) &&
                    Objects.equals(name, place.name) &&
                    Objects.equals(placeId, place.placeId) &&
                    Objects.equals(rating, place.rating) &&
                    Objects.equals(vicinity, place.vicinity);
        }

        @Override
        public int hashCode() {

            return Objects.hash(geometry, icon, id, name, placeId, rating, vicinity);
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPlaceId() {
            return placeId;
        }

        public Double getRating() {
            return rating;
        }

        public String getVicinity() {
            return vicinity;
        }
    }

    public static class Geometry {

        @Expose
        @SerializedName("location")
        private Location location;

        public Location getLocation() {
            return location;
        }
    }

    public static class Location {

        @Expose
        @SerializedName("lat")
        private Double latitude;

        @Expose
        @SerializedName("lng")
        private Double longitude;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return Objects.equals(latitude, location.latitude) &&
                    Objects.equals(longitude, location.longitude);
        }

        @Override
        public int hashCode() {

            return Objects.hash(latitude, longitude);
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }
    }

}
