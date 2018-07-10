package com.globeandi.toeat.ui.trip.addtrip.restaurant;

public interface RestaurantSelectionNavigator {

    void handleError(Throwable throwable);

    void searchRestaurantByName();

    void searchNearbyRestaurant();

    void goBack();
}
