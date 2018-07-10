package com.globeandi.toeat.ui.section.settings;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import io.reactivex.Scheduler;

/**
 * Created by jay on 3/21/2018.
 */

public class SettingViewModel extends BaseViewModel {
    public SettingViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
    }
}
