package com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class VoteAlertDialogProvider {

    @ContributesAndroidInjector(modules = VoteAlertDialogModule.class)
    abstract VoteAlertDialog provideVoteAlertDialogFactory();

}
