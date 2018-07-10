package com.globeandi.toeat.ui.trip.tripFragment;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.GlobeDateUtils;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

public class TripFragmentViewModel extends BaseViewModel<TripFragmentNavigation> {

    private static final String TAG = "TripFragmentViewModel";

    //Notify if trip is been created
    public final ObservableBoolean isCreated = new ObservableBoolean(false);

    //enable admin features
    public final ObservableBoolean isAdmin = new ObservableBoolean(false);

    //manage join trip
    public final ObservableBoolean isUserJoined= new ObservableBoolean();

    //manage voting mode
    public final ObservableBoolean isVotingMode = new ObservableBoolean();

    public final ObservableBoolean showResults = new ObservableBoolean();

    //manage when user vote
    public final ObservableBoolean userVoted = new ObservableBoolean();

    public final ObservableField<View> checkboxView = new ObservableField<>();

    public final ObservableField<TripResponse.Trip> mTrip = new ObservableField<>();

    //represent the trip
    public final MutableLiveData<TripResponse.Trip> mTripLiveData;

    //**** places to eat
    public final ObservableList<TripResponse.PlacesToEat> mPlaces = new ObservableArrayList<>();

    public final MutableLiveData<List<TripResponse.PlacesToEat>> placesToEatLiveData;

    //maps coordinate
    public final ObservableField<Double> latitud = new ObservableField<>();

    public final ObservableField<Double> longitud = new ObservableField<>();

    //**** users joined img rv
    public final ObservableList<TripResponse.Outing> mOutingsObservableList = new ObservableArrayList<>();

    public final MutableLiveData<List<TripResponse.Outing>> userGoingLiveData;

    //save group id
    public final ObservableField<Long> groupId = new ObservableField<>();

    public MutableLiveData<TripResponse.Trip> getTripLiveData() {
        return mTripLiveData;
    }

    //Notify if user is admin and a trip has not been created
    public TripFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mTripLiveData = new MutableLiveData<>();
        placesToEatLiveData  = new MutableLiveData<>();
        userGoingLiveData = new MutableLiveData<>();
    }

    /*
    Remove user form group/and delete user if is admin
     */
    public void removeUserFromGroup(Long id) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doDeleteGroupCall(id)
                .doOnSuccess(groupResponse -> Log.d(TAG, "response: " + groupResponse))
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(groupResponse -> {
                    setIsLoading(false);
                    getNavigator().alertChangesEvent();
                    getNavigator().onBack();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                })
        );
    }

    /*
    Complete trip
     */
    public void completeTrip(Long tripId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doCompleteTrip(tripId)
                .doOnSuccess(tripResponse -> Log.d(TAG, "response: " + tripResponse))
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(tripResponse -> {
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                })
        );
    }


    /*
    get Active Trip  and user that are going
    use a flatMap to make multiples calls 
    Display list of user that are going
     */
    public void fetchActiveTrip(Long groupId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getActiveTripCall(groupId)
                .doOnSuccess(tripResponse -> {
                    //search for current user actions
                    isUserJoined.set(searchCurrentUserJoined(tripResponse.getTripInfo().getOutings()));
                    userVoted.set(searchCurrentUserVoted(tripResponse.getTripInfo().getOutings()));
                    showResults.set(GlobeDateUtils.isVoteAvailable(tripResponse.getTripInfo().getTrip().getTripDateTime()));
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(tripResponse -> {
                            setIsLoading(false);
                            if(tripResponse != null && tripResponse.getTripEmpty()){
                                isCreated.set(false);
                            }
                            else{
                                //get trip try it with the observable
                                isCreated.set(true);
                                mTripLiveData.setValue(tripResponse.getTripInfo().getTrip());
                                getNavigator().setUpViews(tripResponse.getTripInfo().getTrip());
                                //get the places to eat
                                mPlaces.addAll(tripResponse.getTripInfo().getTrip().getPlacesToEats());
                                //get user going to trip
                  //              userGoingLiveData.postValue(tripResponse.getTripInfo().getOutings());
                            }
                        }
                        , throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                        }));
    }

    /*
    do votes results, temporal fix
     */
    public void  getResults(long tripId){
        getCompositeDisposable().add(getDataManager()
                .getVoteResults(tripId)
                .doOnSuccess(tripResponse -> isUserJoined.set(true))
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(tripResponse -> {
                }, throwable -> {
                    getNavigator().handleError(throwable);
                }));
    }
    /*
    User join trip
     */
    public void joinTrip(long groupId, long tripId) {
        getCompositeDisposable().add(getDataManager().doJoinTripCall(groupId, tripId)
                .doOnSuccess(tripResponse -> {
                    isUserJoined.set(true);
                    //notify when user press back button
                    getDataManager().setTripCrreated(true);
                })
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(tripResponse -> {
                }, throwable -> {
                    getNavigator().handleError(throwable);
                }));
    }

    /*
    User leave trip
     */
    public void unJoinTrip(long groupId, long tripId) {
        getCompositeDisposable().add(getDataManager().doUnJoinTripCall(groupId, tripId)
                .doOnSuccess(tripResponse -> {
                    isUserJoined.set(false);
                    getDataManager().setTripCrreated(true);
                    //show voting list
                    userVoted.set(false);
                })
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(tripResponse -> {
                }, throwable -> {
                    getNavigator().handleError(throwable);
                }));
    }

    public void displayUserGoing() {

    }


    //search current user joined
    public boolean searchCurrentUserJoined(List<TripResponse.Outing> userGoing){
        Long currentId = getDataManager().getCurrentUserId();
        for (TripResponse.Outing user: userGoing) {
            if (user.getUserId().equals(currentId)){
                return true;
            }
        }
        return false;
    }

    public boolean searchCurrentUserVoted(List<TripResponse.Outing> userGoing){
        Long currentId = getDataManager().getCurrentUserId();
        boolean voted = false;
        for (TripResponse.Outing user: userGoing) {
            if (user.getUserId().equals(currentId)){
                voted = user.getVoted();
            }
        }
        return voted;
    }


    /*
    go to create trip activity
     */
    public void onClickGoToTripCreation() {
        getNavigator().openTripCreationActivity();
    }

    /*
     use a flatMap to make multiples calls
     in this -> update the user going list in trip
      */

    public void onClickAddMemberToTrip() {
        getNavigator().joinTripAction();
    }

    public void onClickUnjoinToTrip(){
        getNavigator().unJoinTripAction();
    }
    /*
        Show places to vote
     */
    public void onClickShowVotePlaces() {


    }


}
