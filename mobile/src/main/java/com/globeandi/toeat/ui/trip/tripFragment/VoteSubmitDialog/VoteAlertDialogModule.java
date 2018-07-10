package com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class VoteAlertDialogModule {

    @Provides
    VoteAlertViewModel provideVoteAlertViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new VoteAlertViewModel(dataManager,schedulerProvider);
    }
}
