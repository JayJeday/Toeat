package com.globeandi.toeat.ui.trip.tripFragment;

import android.databinding.ObservableField;
import android.databinding.ObservableLong;
import android.util.Log;
import android.view.View;

import com.globeandi.toeat.data.models.api.TripResponse;

import static com.globeandi.toeat.ui.trip.tripFragment.TripFragment.TAG;

public class TripFragmentVotingItemViewModel {

    public final ObservableLong id;

    public final ObservableField<String> name;

    public final ObservableField<String> vicinity;

    public final VotingItemListener mVotingItemListener;

    public final TripResponse.PlacesToEat mPlaceToEat;

    public TripFragmentVotingItemViewModel( VotingItemListener votingItemListener, TripResponse.PlacesToEat place) {
        this.mPlaceToEat = place;
        this.mVotingItemListener = votingItemListener;
        this.id = new ObservableLong(place.getId());
        this.name = new ObservableField<>(place.getName());
        this.vicinity = new ObservableField<>(place.getVicinity());
    }

    public void onPlaceVotedClicked(View view){
    //Pass this values to adapter view holder
        if(view == null){
            Log.d(TAG, "onPlaceVotedClicked: view is null");
        }
        mVotingItemListener.onVotingSelected(view,mPlaceToEat);
    }

    public interface VotingItemListener{
        void onVotingSelected(View view, TripResponse.PlacesToEat place);
    }
}
