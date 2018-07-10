package com.globeandi.toeat.ui.trip.tripFragment;

import android.arch.lifecycle.ViewModelProvider;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TripFragmentModule {

    @Provides
    TripFragmentViewModel tripViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new TripFragmentViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory provideTripViewModel(TripFragmentViewModel tripFragmentViewModel) {
        return new ViewModelProviderFactory<>(tripFragmentViewModel);
    }

    @Provides
    TripFragmentVotingListAdapter provideVotingListAdapter(){
        return new TripFragmentVotingListAdapter();
    }

    @Provides
    UserGoingAdapter provideUserGoingAdapter(){
        return new UserGoingAdapter();
    }
}
