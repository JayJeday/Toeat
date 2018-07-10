package com.globeandi.toeat.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

public class SearchViewModel extends BaseViewModel<SearchUserItemNavigator> {

    private static final String TAG = "SearchViewModel";

    private final MutableLiveData<List<SearchResponse.User>> userListLiveData;

    //store group id in case rotation re config
    public final ObservableField<Long> groupId = new ObservableField<>();

    public SearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        userListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<SearchResponse.User>> getUserListLiveData() {
        return userListLiveData;
    }

    public void searchUser(Long groupId, String text) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doUserSearch(groupId, text)
                .doOnSuccess(searchResponse ->
                        {
                            if (searchResponse != null && searchResponse.getUsers() != null) {
                                manageInvitationUsers(searchResponse.getUsers(), searchResponse.getInvitesGroupList());
                                userListLiveData.postValue(searchResponse.getUsers());
                            }
                            Log.d(TAG, "response: " + searchResponse);
                        }
                )
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(searchResponse -> {

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    /*
    Set invitations for user that already invited
     */
    private void manageInvitationUsers(List<SearchResponse.User> user, List<InviteResponse.Invite> groupInvites) {
        for (int i = 0; i < groupInvites.size(); i++) {
            for (int j = 0; j < user.size(); j++) {
                if (user.get(j).getId().longValue() == groupInvites.get(i).getUserId().longValue()) {
                    user.get(j).setInvited(true);
                }
            }
        }
    }

    public void inviteUser(Long userId,ObservableField<Boolean> isInvited) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doSendInvite(groupId.get(), userId)
                .doOnSuccess(inviteResponse -> isInvited.set(true)
                )
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(inviteResponse -> {

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void deleteInvite(Long userId,ObservableField<Boolean> isInvited ) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doDeleteInvite(groupId.get(), userId)
                .doOnSuccess(inviteResponse -> {isInvited.set(false);}
                )
                .observeOn(getSchedulerProvider().io())
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(inviteResponse -> {

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}

