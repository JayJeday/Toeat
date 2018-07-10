package com.globeandi.toeat.ui.trip.addtrip;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TripCreationFragmentProvider {

    @ContributesAndroidInjector(modules = TripCreationFragmentModule.class)
    abstract TripCreationFragment provideTripCreationFragmentFactory();
}
