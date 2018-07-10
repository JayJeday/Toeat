package com.globeandi.toeat.ui.splash;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

public class SplashViewModel extends BaseViewModel<SplashNavigator>{


    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void redirectToActivity(){
        if (getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()){
            getNavigator().openLoginActivity();
        }else {
            getNavigator().openSectionActivity();
        }
    }
}
