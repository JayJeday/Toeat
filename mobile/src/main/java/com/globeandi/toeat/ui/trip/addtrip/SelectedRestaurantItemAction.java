package com.globeandi.toeat.ui.trip.addtrip;

import com.globeandi.toeat.data.models.api.PlacesApiResponse;

public interface SelectedRestaurantItemAction {

    /*
    on place selected change map location and marker
     */
    void onPlaceSelected(PlacesApiResponse.Place place);
}
