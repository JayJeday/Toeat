package com.globeandi.toeat.ui.registration;

/**
 * Created by jay on 3/21/2018.
 */

import android.arch.lifecycle.ViewModel;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class RegistrationActivityModule {

    @Provides
    RegistrationViewModel provideRegistrationViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new RegistrationViewModel(dataManager,scheduler);
    }


}
