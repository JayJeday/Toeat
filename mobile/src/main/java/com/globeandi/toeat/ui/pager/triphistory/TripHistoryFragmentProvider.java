package com.globeandi.toeat.ui.pager.triphistory;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class TripHistoryFragmentProvider {

    @ContributesAndroidInjector(modules = TripHistoryFragmentModule.class)
    abstract TripHistoryFragment provideTripHistoryFragmentFactory();
}
