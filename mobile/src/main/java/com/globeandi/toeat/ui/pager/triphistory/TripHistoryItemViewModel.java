package com.globeandi.toeat.ui.pager.triphistory;

import android.databinding.ObservableField;

import com.globeandi.toeat.data.models.api.TripResponse;


public class TripHistoryItemViewModel {

    public final ObservableField<String> title;
    public final ObservableField<String> placeName;
    public final ObservableField<String> vicinity;
    public final ObservableField<String> datetime;

    public TripHistoryItemViewModel(TripResponse.Trip trip) {
        this.title = new ObservableField<>(trip.getTitle());
        //places are going to have only 1 item when trip is completed
        this.placeName = new ObservableField<>(trip.getPlacesToEats().get(0).getName());
        this.vicinity = new ObservableField<>(trip.getPlacesToEats().get(0).getVicinity());
        this.datetime = new ObservableField<>(trip.getTripDateTime());
    }
}
