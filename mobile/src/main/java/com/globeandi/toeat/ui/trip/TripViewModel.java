package com.globeandi.toeat.ui.trip;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

public class TripViewModel extends BaseViewModel<TripActivityNavigation>{


    public TripViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

}
