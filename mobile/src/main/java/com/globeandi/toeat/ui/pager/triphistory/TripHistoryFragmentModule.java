package com.globeandi.toeat.ui.pager.triphistory;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class TripHistoryFragmentModule {

        @Provides
    TripHistoryViewModel TripHistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
            return new TripHistoryViewModel(dataManager,schedulerProvider);
        }

        @Provides
        TripHistoryAdapter provideTripHistoryAdapter(){
            return new TripHistoryAdapter();
        }

    @Provides
    ViewModelProvider.Factory provideTripHistoryViewModel (TripHistoryViewModel tripHistoryViewModel){
        return new ViewModelProviderFactory<>(tripHistoryViewModel);
    }

}
