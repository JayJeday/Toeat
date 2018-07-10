package com.globeandi.toeat.ui.trip.tripFragment;

import android.databinding.ObservableField;

import com.globeandi.toeat.data.models.api.SearchResponse;

public class TripFragmentUserGoingItemViewModel {

    public final ObservableField<String> userImgProfile;

    public final SearchResponse.User mUser;

    public TripFragmentUserGoingItemViewModel(SearchResponse.User user) {
        this.mUser = user;
        userImgProfile = new ObservableField<>(mUser.getProfile_picture());
    }
}
