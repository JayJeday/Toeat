package com.globeandi.toeat.ui.section;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public class SectionActivityModule {

    @Provides
    SectionViewModel provideSectionViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new SectionViewModel(dataManager,scheduler);
    }
}
