package com.globeandi.toeat.ui.section.invite;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by jay on 3/21/2018.
 */

public class InviteViewModel extends BaseViewModel<InviteNavigator> {

    private static final String TAG = "InviteViewModel";

    public final MutableLiveData<List<InviteResponse.Invite>> inviteListLiveData;

    public final ObservableBoolean empty = new ObservableBoolean(false);

    //will represent the actual list
    public final ObservableList<InviteResponse.Invite> mInviteObservableList = new ObservableArrayList<>();

    public InviteViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
        inviteListLiveData = new MutableLiveData<>();
        fetchInvites();
    }


    public MutableLiveData<List<InviteResponse.Invite>> getInviteListLiveData() {
        return inviteListLiveData;
    }

    public void fetchInvites(){
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getGroupInvites()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(inviteResponse -> {
                                    if ( inviteResponse != null && inviteResponse.getData() != null){
                                        inviteListLiveData.setValue(inviteResponse.getData());
                                        if(inviteListLiveData.getValue().isEmpty()){
                                            empty.set(true);
                                        }else{
                                            empty.set(false);
                                        }
                                    }
                                    setIsLoading(false);
                                },
                                throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleError(throwable);
                                }));
    }

    public void joinGroup(Long id){
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().doJoinGroup(id)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(inviteResponse -> {
                                    Log.d(TAG, "joinGroup: " + inviteResponse);
                                    setIsLoading(false);
                                },
                                throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleError(throwable);
                                }));
    }

    /*
    When user cancel the invitation
     */
    public void notJoinGroup(Long id){
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().doDeleteInvite(id,getDataManager().getCurrentUserId())
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(inviteResponse -> {
                                    Log.d(TAG, "joinGroup: " + inviteResponse);
                                    setIsLoading(false);
                                },
                                throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleError(throwable);
                                }));
    }
}
