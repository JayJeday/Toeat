package com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TripDatePickerModule {

    @Provides
   TripDatePickerViewModel provideTripDatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new TripDatePickerViewModel (dataManager, schedulerProvider);
    }
}
