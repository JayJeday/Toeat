package com.globeandi.toeat.ui.section.about;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public class AboutFragmentModule {

    @Provides
    AboutViewModel provideAboutViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new AboutViewModel(dataManager,scheduler);
    }

}
