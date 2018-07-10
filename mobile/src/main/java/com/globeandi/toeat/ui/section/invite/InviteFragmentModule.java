package com.globeandi.toeat.ui.section.invite;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.globeandi.toeat.ViewModelProviderFactory;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jay on 3/21/2018.
 */
@Module
public class InviteFragmentModule {

    @Provides
    InviteViewModel inviteViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new InviteViewModel(dataManager,scheduler);
    }

    @Provides
    InviteAdapter providesInviteAdapter(){
        return new InviteAdapter();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(InviteFragment groupFragment){
        return new LinearLayoutManager(groupFragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideInviteViewModel (InviteViewModel inviteViewModel){
        return new ViewModelProviderFactory<>(inviteViewModel);
    }
}
