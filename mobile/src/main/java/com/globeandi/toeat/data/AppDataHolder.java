package com.globeandi.toeat.data;

import com.globeandi.toeat.data.remote.AppApiHelper;

public class AppDataHolder {

    private AppApiHelper mAppDataManager;

    public AppApiHelper appDataManager(){
        return  mAppDataManager;
    }

    public void setAppDataManager(AppApiHelper appDataManager) {
        mAppDataManager = appDataManager;
    }
}
