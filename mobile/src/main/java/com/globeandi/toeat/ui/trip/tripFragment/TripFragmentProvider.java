package com.globeandi.toeat.ui.trip.tripFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TripFragmentProvider {

    @ContributesAndroidInjector(modules = TripFragmentModule.class)
    abstract TripFragment provideGroupTripFragmentFactory();
}
