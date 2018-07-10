package com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog;

import android.databinding.ObservableField;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.MessageEvents;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import org.greenrobot.eventbus.EventBus;

public class VoteAlertViewModel extends BaseViewModel<VoteAlertCallback> {

    public final ObservableField<Long> placeId = new ObservableField<>();


    public VoteAlertViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void submitVote(long groupId, long tripId, long placeId) {
        //Api call to the service
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doSubmitVoteCall(groupId, tripId, placeId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(voteResponse -> {
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                })
        );
    }

    //********* listener *****************
    public void onVoteSubmitClick() {
        getNavigator().submitVote();
    }

    public void onVoteCancelClick() {
        getNavigator().dismissDialogOnCancel();
    }
}
