package com.globeandi.toeat.ui.trip.addtrip;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.groups.GroupsViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class TripCreationFragmentModule {


    @Provides
    ViewModelProvider.Factory provideTripCreationViewModel(TripCreationViewModel tripCreationViewModel) {
        return new ViewModelProviderFactory<>(tripCreationViewModel);
    }

    @Provides
    TripCreationViewModel TripCreationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new TripCreationViewModel(dataManager, schedulerProvider);
    }

    @Provides
    SelectedRestaurantAdapter provideSelectedRestaurantAdapter(){
        return new SelectedRestaurantAdapter();
    }

}
