package com.globeandi.toeat.ui.trip.addtrip.restaurant;

import android.arch.lifecycle.ViewModelProvider;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.custom.WrapContentLinearLayoutManager;
import com.globeandi.toeat.ui.trip.addtrip.TripCreationFragment;
import com.globeandi.toeat.util.rx.SchedulerProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public class RestaurantSelectionModule {

    @Provides
    ViewModelProvider.Factory provideRestaurantSelectionViewModel(RestaurantSelectionViewModel restaurantSelectionViewModel) {
        return new ViewModelProviderFactory<>(restaurantSelectionViewModel);
    }

    @Provides
    RestaurantSelectionViewModel RestaurantSelectionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new RestaurantSelectionViewModel(dataManager, schedulerProvider);
    }

    @Provides
    Adapter provideRestaurantAdapter(){
        return new Adapter();

    }

    @Provides
    WrapContentLinearLayoutManager provideLinearLayoutManager(RestaurantSelectionFragment restaurantSelectionFragment) {
        return new WrapContentLinearLayoutManager(restaurantSelectionFragment.getActivity());
    }

    //get coarse location
    @Provides
    FusedLocationProviderClient provideLocationProviderClient(RestaurantSelectionFragment restaurantSelectionFragment){
        return LocationServices.getFusedLocationProviderClient(restaurantSelectionFragment.getBaseActivity());
    }

}
