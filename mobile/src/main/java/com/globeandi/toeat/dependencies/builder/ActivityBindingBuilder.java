package com.globeandi.toeat.dependencies.builder;

import com.globeandi.toeat.ui.groups.addgroup.GroupCreationActivity;
import com.globeandi.toeat.ui.groups.addgroup.GroupCreationActivityModule;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripFragmentProvider;
import com.globeandi.toeat.ui.search.SearchActivity;
import com.globeandi.toeat.ui.search.SearchActivityModule;
import com.globeandi.toeat.ui.trip.TripActivity;

import com.globeandi.toeat.ui.trip.TripActivityModule;
import com.globeandi.toeat.ui.trip.tripFragment.TripFragmentProvider;
import com.globeandi.toeat.ui.groups.GroupFragmentProvider;

import com.globeandi.toeat.ui.pager.triphistory.TripHistoryFragmentProvider;
import com.globeandi.toeat.ui.login.LoginActivity;
import com.globeandi.toeat.ui.login.LoginActivityModule;

import com.globeandi.toeat.ui.registration.RegistrationActivity;
import com.globeandi.toeat.ui.registration.RegistrationActivityModule;
import com.globeandi.toeat.ui.section.invite.InviteFragmentProvider;
import com.globeandi.toeat.ui.section.MainActivity;
import com.globeandi.toeat.ui.section.SectionActivityModule;
import com.globeandi.toeat.ui.section.about.AboutFragmentProvider;
import com.globeandi.toeat.ui.section.settings.SettingFragmentProvider;
import com.globeandi.toeat.ui.splash.SplashActivity;
import com.globeandi.toeat.ui.splash.SplashActivityModule;
import com.globeandi.toeat.ui.trip.addtrip.TripCreationFragmentProvider;
import com.globeandi.toeat.ui.trip.addtrip.restaurant.RestaurantSelectionProvider;
import com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog.VoteAlertDialogProvider;
import com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker.TripDatePickerDialogProvider;
import com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker.TripTimePickerDialogProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jay on 3/20/2018.
 * generate dagger sub component for activities
 */
@Module
public abstract class ActivityBindingBuilder {

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = RegistrationActivityModule.class)
    abstract RegistrationActivity bindRegistrationActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector(modules = GroupCreationActivityModule.class)
    abstract GroupCreationActivity bindGroupCreationActivity();

    @ContributesAndroidInjector(modules = {
            SectionActivityModule.class,
            AboutFragmentProvider.class,
            InviteFragmentProvider.class,
            SettingFragmentProvider.class,
            GroupFragmentProvider.class,
            TripHistoryFragmentProvider.class,
            UpcomingTripFragmentProvider.class
    })
    abstract MainActivity bindSectionActivity();

    @ContributesAndroidInjector(modules = {
            TripActivityModule.class,
            TripFragmentProvider.class,
            TripDatePickerDialogProvider.class,
            TripTimePickerDialogProvider.class,
            RestaurantSelectionProvider.class,
            TripCreationFragmentProvider.class,
            VoteAlertDialogProvider.class
    })
    abstract TripActivity bindTripActivity();

}
