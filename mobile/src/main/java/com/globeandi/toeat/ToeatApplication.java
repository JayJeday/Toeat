package com.globeandi.toeat;

import android.app.Activity;
import android.app.Application;


import com.facebook.stetho.Stetho;
import com.globeandi.toeat.dependencies.component.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by jay on 3/20/2018.
 */

public class ToeatApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
////            // This process is dedicated to LeakCanary for heap analysis.
////            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//
//        //Instantiating app Component
//        Stetho.initializeWithDefaults(this);
        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


}
