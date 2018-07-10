package com.globeandi.toeat.ui.trip.tripFragment;

import com.globeandi.toeat.data.models.api.TripResponse;

public interface TripFragmentNavigation {

    void handleError(Throwable throwable);

    void alertChangesEvent();

    void openTripCreationActivity();

    void onBack();

    void setUpViews(TripResponse.Trip trip);

    void joinTripAction();

    void unJoinTripAction();
}
