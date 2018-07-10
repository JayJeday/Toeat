package com.globeandi.toeat.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;


import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by jay on 3/17/2018.
 * All viewModels are going to inherits from here
 */

public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    //Todo:: remove generic functionality for navigator -> using live event instead
    private N mNavigator;

    //container to clear subscriptions
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        //every view model will have a compose object
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        //dispose resources(subscriptions) when view model is about to be destroyed
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading){
        mIsLoading.set(isLoading);
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        mNavigator = navigator;
    }
}
