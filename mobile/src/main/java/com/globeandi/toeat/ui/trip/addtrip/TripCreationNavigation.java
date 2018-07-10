package com.globeandi.toeat.ui.trip.addtrip;

public interface TripCreationNavigation {

    void handleError(Throwable throwable);

    void createTrip();

    void openTripDatePicker();

    void openTripTimePicker();

    void alertChangesEvent();

    void openTripRestaurantSelector();

    void goBack();

}
