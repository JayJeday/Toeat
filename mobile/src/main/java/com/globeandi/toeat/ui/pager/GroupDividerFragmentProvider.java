package com.globeandi.toeat.ui.pager;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class GroupDividerFragmentProvider {

    @ContributesAndroidInjector(modules = GroupDividerFragmentModule.class)
    abstract GroupDividerFragment provideGroupDividerFragmentFactory();
}
