package com.globeandi.toeat.ui.trip;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TripActivityModule {

    @Provides
    TripViewModel provideTripViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new TripViewModel(dataManager,schedulerProvider);
    }

}
