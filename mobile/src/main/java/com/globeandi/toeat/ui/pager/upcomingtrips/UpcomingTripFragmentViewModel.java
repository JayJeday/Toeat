package com.globeandi.toeat.ui.pager.upcomingtrips;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

public class UpcomingTripFragmentViewModel extends BaseViewModel<UpcomingTripsNavigator> {

    //mutable list observer will be notify when list change on server
    private final MutableLiveData<List<TripResponse.Trip>> tripLiveData;

    //notify when upcoming list is empty
    public final ObservableBoolean empty = new ObservableBoolean(false);

    //manage items displayed in the list
    public final ObservableList<TripResponse.Trip> UpcomingTripObservableArrayList = new ObservableArrayList<>();

    public UpcomingTripFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider){
        super(dataManager,schedulerProvider);
        tripLiveData = new MutableLiveData<>();
        fetchUpcomingTrips();
    }

    public MutableLiveData<List<TripResponse.Trip>> getUpcomingTripLiveData() {
        return tripLiveData;
    }

    public void addTripItems(List<TripResponse.Trip> trips){
        UpcomingTripObservableArrayList.addAll(trips);
    }

    public void fetchUpcomingTrips(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUpcomingTripsCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(upcomingTripsResponse -> {
                    setIsLoading(false);
                    if(upcomingTripsResponse != null && upcomingTripsResponse.getUpcomingTrips() != null){
                        tripLiveData.setValue(upcomingTripsResponse.getUpcomingTrips());
                        if(tripLiveData.getValue().isEmpty()){
                            empty.set(true);
                        }else{
                            empty.set(false);
                        }
                    }
                },throwable -> {
                    setIsLoading(false);
                    getNavigator().handleErrors(throwable);
                }));
    }

    public boolean isTripCreated(){
        return getDataManager().isTripCreated();
    }

    public void setTripCreated(){
        getDataManager().setTripCrreated(false);
    }
}
