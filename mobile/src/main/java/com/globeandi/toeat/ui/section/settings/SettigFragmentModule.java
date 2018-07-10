package com.globeandi.toeat.ui.section.settings;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public class SettigFragmentModule {

    @Provides
    SettingViewModel provideSettingViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new SettingViewModel(dataManager,scheduler);
    }
}
