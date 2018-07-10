package com.globeandi.toeat.ui.section.invite;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public abstract class InviteFragmentProvider {

    @ContributesAndroidInjector(modules = InviteFragmentModule.class)
    abstract InviteFragment provideInviteFragmentFactory();
}
