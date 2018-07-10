package com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TripTimePickerDialogModule {

    @Provides
    TripTimePickerViewModel providerTripTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new TripTimePickerViewModel(dataManager,schedulerProvider);
    }
}
