package com.globeandi.toeat.ui.section.about;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.ui.login.LoginNavigator;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 */

public class AboutViewModel extends BaseViewModel<AboutNavigator> {


    public AboutViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
    }

    public void notVailable(){
        getNavigator().notAvailable();
    }
}
