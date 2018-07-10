package com.globeandi.toeat.ui.section.about;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public abstract class AboutFragmentProvider {

    @ContributesAndroidInjector(modules = AboutFragmentModule.class)
    abstract AboutFragment provideAboutFragmentFactory();

}
