package com.globeandi.toeat.ui.pager;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupDividerFragmentModule {

    //manage fragment inside other fragment
    @Provides
    GroupDividerPagerAdapter provideGroupDividerPagerAdapter(GroupDividerFragment fragment){
        return new GroupDividerPagerAdapter(fragment.getChildFragmentManager());
    }

    @Provides
    GroupDividerViewModel provideGroupDividerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new GroupDividerViewModel(dataManager,schedulerProvider);
    }


}
