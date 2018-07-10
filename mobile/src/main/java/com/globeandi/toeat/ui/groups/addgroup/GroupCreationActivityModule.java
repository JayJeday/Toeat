package com.globeandi.toeat.ui.groups.addgroup;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupCreationActivityModule {

    @Provides
    GroupCreationViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new GroupCreationViewModel(dataManager, scheduler);
    }
}
