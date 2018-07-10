package com.globeandi.toeat.ui.search;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new SearchViewModel(dataManager,schedulerProvider);
    }

    @Provides
    SearchUserAdapter provideSearchUserAdapter(){
        return new SearchUserAdapter();
    }
}
