package com.globeandi.toeat.ui.pager.upcomingtrips;

import android.arch.lifecycle.ViewModelProvider;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;

import com.globeandi.toeat.util.rx.SchedulerProvider;


import dagger.Module;
import dagger.Provides;

@Module
public class UpcomingTripFragmentModule {

    @Provides
    UpcomingTripFragmentViewModel TripHistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new UpcomingTripFragmentViewModel(dataManager, schedulerProvider);
    }

    @Provides
    UpcomingTripAdapter provideUpcomingTripAdapter() {
        return new UpcomingTripAdapter();
    }

    @Provides
    ViewModelProvider.Factory provideUpcomingTripViewModel(UpcomingTripFragmentViewModel upcomingTripFragmentViewModel) {
        return new ViewModelProviderFactory<>(upcomingTripFragmentViewModel);
    }
}
