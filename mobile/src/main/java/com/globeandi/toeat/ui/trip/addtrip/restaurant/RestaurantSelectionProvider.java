package com.globeandi.toeat.ui.trip.addtrip.restaurant;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RestaurantSelectionProvider {

    @ContributesAndroidInjector(modules = RestaurantSelectionModule.class)
    abstract RestaurantSelectionFragment provideRestaurantSelectionFactory();
}
