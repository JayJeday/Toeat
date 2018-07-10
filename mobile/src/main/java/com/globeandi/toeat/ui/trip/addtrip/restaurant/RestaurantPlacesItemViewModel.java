package com.globeandi.toeat.ui.trip.addtrip.restaurant;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;

import androidx.recyclerview.selection.OnItemActivatedListener;

public class RestaurantPlacesItemViewModel {

    public final ObservableField<String> id;

    public final ObservableField<String> name;

    public final ObservableField<String> vicinity;

    public final ObservableField<Double> rating;


    public final PlacesApiResponse.Place mPlace;

    public final ObservableField<String> icon;

    //manage item background onn selection
    public final ObservableInt itemBackground = new ObservableInt(R.color.white);

    public RestaurantPlacesItemViewModel(PlacesApiResponse.Place place) {
        this.mPlace = place;
        this.id = new ObservableField<>();
        this.name = new ObservableField<>(mPlace.getName());
        this.vicinity = new ObservableField<>(mPlace.getVicinity());
        this.rating = new ObservableField<>(mPlace.getRating());
        //TODO::convert icon using glide
        this.icon = new ObservableField<>(mPlace.getIcon());
    }


}
