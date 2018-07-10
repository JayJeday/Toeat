package com.globeandi.toeat.ui.trip;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.ui.trip.addtrip.SelectedRestaurantItemAction;

public class TripCreationItemViewModel {

    public final ObservableField<String> id;

    public final ObservableField<String> name;

    public final ObservableField<String> vicinity;

    public final ObservableField<Double> rating;

    public final SelectedRestaurantItemAction mRestaurantItemAction;

    public final PlacesApiResponse.Place mPlace;

    public final ObservableField<String> icon;

    //manage item background onn selection
    public final ObservableInt itemBackground = new ObservableInt(R.color.white);

    public TripCreationItemViewModel(PlacesApiResponse.Place place,SelectedRestaurantItemAction itemAction) {
        this.mRestaurantItemAction = itemAction;
        this.mPlace = place;
        this.id = new ObservableField<>();
        this.name = new ObservableField<>(mPlace.getName());
        this.vicinity = new ObservableField<>(mPlace.getVicinity());
        this.rating = new ObservableField<>(mPlace.getRating());
        //TODO::convert icon using glide
        this.icon = new ObservableField<>(mPlace.getIcon());
    }

    public void onPlaceSelected(){
        mRestaurantItemAction.onPlaceSelected(mPlace);
    }

}
