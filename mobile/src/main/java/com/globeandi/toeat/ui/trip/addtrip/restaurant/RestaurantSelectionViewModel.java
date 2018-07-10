package com.globeandi.toeat.ui.trip.addtrip.restaurant;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

public class RestaurantSelectionViewModel extends BaseViewModel<RestaurantSelectionNavigator> {

    public final ObservableBoolean isVotingPhase = new ObservableBoolean();

    //list to the recycle view list item
    public final ObservableList<PlacesApiResponse.Place> mPlaceObservableList = new ObservableArrayList<>();

    //selected places holder
    public final ObservableList<PlacesApiResponse.Place> mSelectedPlacesHolder = new ObservableArrayList<>();

    public final ObservableField<String> latAndLong = new ObservableField<>();

    //represent the places that comes from the server
    private final MutableLiveData<List<PlacesApiResponse.Place>> placeListLiveData;

    private static final String TAG = "RestaurantSelectionView";


    public RestaurantSelectionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        placeListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<PlacesApiResponse.Place>> getPlaceListLiveData() {
        return placeListLiveData;
    }

    public ObservableList<PlacesApiResponse.Place> getPlaceObservableList() {
        return mPlaceObservableList;
    }

    public void searchRestaurantByName(String name) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getRestaurantSpecificSearch("restaurant", latAndLong.get(), name, 15000)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSuccess(placesApiResponse -> {
                    if (placesApiResponse != null && placesApiResponse.getPlaces() != null) {
                        placeListLiveData.setValue(placesApiResponse.getPlaces());
                    }
                    setIsLoading(false);
                })
                .subscribe(placesApiResponse -> {
                            setIsLoading(false);
                        }
                        , throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                        }));
    }
//"40.744612, -74.038049"
    public void searchNearbyRestaurant() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getRestaurantNearbySearch(latAndLong.get(), "distance", "restaurant")
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSuccess(placesApiResponse -> {
                            //verify the call
                            if (placesApiResponse != null && placesApiResponse.getPlaces() != null) {
                                placeListLiveData.setValue(placesApiResponse.getPlaces());
                            }
                            Log.d(TAG, "searchNearbyRestaurant: " + placesApiResponse);
                        }
                )

                .subscribe(placesApiResponse -> {
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                })
        );
    }

    /*
        Onclick event for finding specific restaurant
    */
    public void onClickSearchSpecificRestaurant() {
        getNavigator().searchRestaurantByName();
    }

    public void onClickSearchNearbyRestaurants(){

        getNavigator().searchNearbyRestaurant();
    }

    public void goBack(){
        getNavigator().goBack();
    }

    //populate list
    public void addPlacesItemsToList(List<PlacesApiResponse.Place> places) {
        mPlaceObservableList.clear();
        mPlaceObservableList.addAll(places);
    }
}
