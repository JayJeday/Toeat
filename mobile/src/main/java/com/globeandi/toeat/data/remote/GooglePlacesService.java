package com.globeandi.toeat.data.remote;

import com.globeandi.toeat.data.models.api.PlacesApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {
//TODO::change key to constant
    /*
    Specify a specify distance
    rank by-> distance
     */
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyAzTyx-WgeUSZ1RiTOxEpL2aCr_gpFRHT8")
    Single<PlacesApiResponse>getSpecificRestaurantByName(@Query("type") String type, @Query("location") String location,@Query("keyword") String name,@Query("radius") int radius);

    /*
    Select nearby restaurants to eat
     */
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyAzTyx-WgeUSZ1RiTOxEpL2aCr_gpFRHT8")
    Single<PlacesApiResponse>getNearbyRestaurant(@Query("location") String location, @Query("rankby") String rankBy, @Query("type")String type);

}
