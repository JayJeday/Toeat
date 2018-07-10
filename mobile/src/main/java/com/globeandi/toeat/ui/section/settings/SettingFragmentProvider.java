package com.globeandi.toeat.ui.section.settings;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public abstract class SettingFragmentProvider {

    @ContributesAndroidInjector(modules = SettigFragmentModule.class)
    abstract SettingFragment SettingFragmentFactory();
}
