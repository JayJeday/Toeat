package com.globeandi.toeat.ui.login;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 * a sub component of AppComponent that provide a LoginViewModel
 */
@Module
public class LoginActivityModule  {

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider scheduler){
        return new LoginViewModel(dataManager, scheduler);
    }
}
