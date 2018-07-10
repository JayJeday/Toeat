package com.globeandi.toeat.ui.groups;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public abstract class GroupFragmentProvider {

    @ContributesAndroidInjector(modules = GroupFragmentModule.class)
    abstract GroupsFragment provideGroupFragmentFactory();
}
