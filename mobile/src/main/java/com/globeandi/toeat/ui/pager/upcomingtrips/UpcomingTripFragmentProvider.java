package com.globeandi.toeat.ui.pager.upcomingtrips;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UpcomingTripFragmentProvider {

    @ContributesAndroidInjector(modules = UpcomingTripFragmentModule.class)
    abstract UpcomingTripFragment provideUpcomingTripFragmentFactory();
}
