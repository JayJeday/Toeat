package com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TripDatePickerDialogProvider {

    @ContributesAndroidInjector(modules = TripDatePickerModule.class)
    abstract TripDatePickerDialog provideTripDatePickerDialogFactory();

}
