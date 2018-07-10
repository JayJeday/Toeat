package com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TripTimePickerDialogProvider {

    @ContributesAndroidInjector(modules = TripTimePickerDialogModule.class)
    abstract TripTimePickerDialog provideTripTimePickerDialogFactory();
}
