package com.globeandi.toeat.ui.groups;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.pager.GroupDividerFragment;
import com.globeandi.toeat.ui.pager.GroupDividerPagerAdapter;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public class GroupFragmentModule {

    @Provides
    GroupsViewModel groupViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        return new GroupsViewModel(dataManager, scheduler);
    }

    @Provides
    GroupsAdapter provideGroupAdapter() {
        return new GroupsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(GroupsFragment groupFragment) {
        return new LinearLayoutManager(groupFragment.getActivity());
    }

    //ViewModel requiring constructor parameters
    // is instantiated via a custom factory class that implements ViewModelProvider.Factory interface
    @Provides
    ViewModelProvider.Factory provideGroupViewModel(GroupsViewModel groupViewModel) {
        return new ViewModelProviderFactory<>(groupViewModel);
    }

    @Provides
    GroupDividerPagerAdapter provideGroupDividerPagerAdapter(GroupsFragment fragment){
        return new GroupDividerPagerAdapter(fragment.getChildFragmentManager());
    }
}
