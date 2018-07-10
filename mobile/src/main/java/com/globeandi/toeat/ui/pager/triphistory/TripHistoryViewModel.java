package com.globeandi.toeat.ui.pager.triphistory;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.ui.pager.TripHistoryNavigator;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

public class TripHistoryViewModel extends BaseViewModel<TripHistoryNavigator> {

    //mutable list observer will be notify when list change on server
    private final MutableLiveData<List<TripResponse.Trip>> tripLiveData;

    //manage items displayed in the list
    public final ObservableList<TripResponse.Trip> tripObservableArrayList = new ObservableArrayList<>();

    //notify when tripHistory list is empty
    public final ObservableBoolean empty = new ObservableBoolean(false);

    public TripHistoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        tripLiveData = new MutableLiveData<>();
        fetchTripsHistory();
    }

    public MutableLiveData<List<TripResponse.Trip>> getTripLiveData() {
        return tripLiveData;
    }

    public void addTripItems(List<TripResponse.Trip> trips){
        tripObservableArrayList.addAll(trips);
    }
    public void fetchTripsHistory(){
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getTripHistoryCall()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(tripHistoryResponse -> {
                        setIsLoading(false);
                        if(tripHistoryResponse != null && tripHistoryResponse.getTripsHistory() != null){
                            tripLiveData.setValue(tripHistoryResponse.getTripsHistory());
                            if(tripLiveData.getValue().isEmpty()){
                                empty.set(true);
                            }else {
                                empty.set(false);
                            }
                        }
              },throwable -> {
                        setIsLoading(false);
                        getNavigator().handleErrors(throwable);
                    }));
    }

}
