package com.globeandi.toeat.ui.trip.addtrip;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.util.Log;


import com.globeandi.toeat.data.DataManager;

import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.CommonUtils;
import com.globeandi.toeat.util.GlobeDateUtils;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripCreationViewModel extends BaseViewModel<TripCreationNavigation> {

    private static final String TAG = "TripCreationViewModel";

    public final ObservableBoolean votingPhase = new ObservableBoolean();
    public final ObservableBoolean isMapSelected = new ObservableBoolean(false);

    public final ObservableField<String> datePickerResult = new ObservableField<>();
    public final ObservableField<String> timePickerResult = new ObservableField<>();

    public final ObservableList<PlacesApiResponse.Place> selectedPlaces = new ObservableArrayList<>();

    public final MutableLiveData<List<PlacesApiResponse.Place>> liveItemList;

    public TripCreationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        liveItemList = new MutableLiveData<>();
    }

    public Set<String> tripCreatioonFieldsValidation(String title, String date, String time) {
        Set<String> failuresFields = new HashSet<>();
        if (TextUtils.isEmpty(title)) {
            failuresFields.add("title");
        }
        if (TextUtils.isEmpty(date)) {
            failuresFields.add("date");
        }
        if (TextUtils.isEmpty(time)){
            failuresFields.add("time");
        }
        if (selectedPlaces.isEmpty()){
            failuresFields.add("places");
        }
        if(votingPhase.get() && selectedPlaces.size() == 1 || !votingPhase.get() && selectedPlaces.size() == 4){
            failuresFields.add("places");
        }
        if (date != null && time != null && !GlobeDateUtils.isTripDateValid(date,time)){
            failuresFields.add("date");
            failuresFields.add("time");
        }
        return failuresFields;
    }

    public TripResponse.Trip setUpTrip(String name, boolean isCarpooled) {
        //Refactor this to avoid duplication
        TripResponse.Trip trip = new TripResponse.Trip();
        trip.setTitle(name);
        trip.setCarpool(isCarpooled);
        trip.setTripDate(datePickerResult.get());
        trip.setTripTime(timePickerResult.get());
        trip.setVoting(votingPhase.get());

        //format trip date and time to set dateTime
        if(trip.getTripDate() != null && trip.getTripTime()!= null){
            trip.setTripDateTime(GlobeDateUtils.setDateTimeToServer(trip.getTripDate(),trip.getTripTime()));
        }


        //manage place
        //TODO:: use flat map to simplify the list
        List<TripResponse.PlacesToEat> places = new ArrayList<>();
        for (PlacesApiResponse.Place place : selectedPlaces) {
            TripResponse.PlacesToEat placeToEat = new TripResponse.PlacesToEat();
            placeToEat.setName(place.getName());
            placeToEat.setVicinity(place.getVicinity());
            placeToEat.setLatitude(place.getGeometry().getLocation().getLatitude().toString());
            placeToEat.setLongitude(place.getGeometry().getLocation().getLongitude().toString());
            places.add(placeToEat);
        }
        trip.setPlacesToEats(places);

        return  trip;
    }

    public void createTrip(Long groupId, TripResponse.Trip trip) {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().doCreateTripCall(groupId, trip)
                        .doOnSuccess(tripResponse ->{
                                    getDataManager().setTripCrreated(true);
                                    getNavigator().alertChangesEvent();
                                    goBack();
                                }
                        )
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(tripResponse -> {
                                    setIsLoading(false);
                                },
                                throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleError(throwable);
                                }));
    }

    public void switchChanged(boolean isChecked) {
        votingPhase.set(isChecked);

    }

    public void addSelectedPlaces(List<PlacesApiResponse.Place> places) {
        selectedPlaces.clear();
        selectedPlaces.addAll(places);
    }

    /*
            OnClick Events
     */

    public void onClickViewDatePicker() {
        getNavigator().openTripDatePicker();
    }

    public void onClickViewTimePicker() {
        getNavigator().openTripTimePicker();
    }

    /*
        Open Google Maps or places to pick restaurant
     */

    public void onClickViewRestaurantPicker() {
        getNavigator().openTripRestaurantSelector();
    }


    public void onCreateTrip() {
        getNavigator().createTrip();
    }

    public void goBack(){
        getNavigator().goBack();
    }
}
