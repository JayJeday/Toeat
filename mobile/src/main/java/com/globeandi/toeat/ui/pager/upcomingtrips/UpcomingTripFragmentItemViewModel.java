package com.globeandi.toeat.ui.pager.upcomingtrips;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.globeandi.toeat.data.models.api.TripResponse;

public class UpcomingTripFragmentItemViewModel {

    public final ObservableField<String> title;

    public final ObservableField numbersOfUser;

    public final ObservableField<String> dateTime;



    public UpcomingTripFragmentItemViewModel(TripResponse.Trip trip) {
        this.title = new ObservableField<>(trip.getTitle());
        this.numbersOfUser = new ObservableField(trip.getNumUsers().toString());
        this.dateTime = new ObservableField<>(trip.getTripDateTime());
    }

}
