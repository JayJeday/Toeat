package com.globeandi.toeat.ui.section;


import android.databinding.ObservableField;
import android.text.TextUtils;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;


/**
 * Created by jay on 3/21/2018.
 */

public class SectionViewModel extends BaseViewModel<SectionNavigator> {

    private static final String TAG = "SectionViewModel";

    public final ObservableField<String> appVersion = new ObservableField<>();

    public final ObservableField<String> userEmail = new ObservableField<>();

    public final ObservableField<String> userName = new ObservableField<>();

    public final ObservableField<String> userProfilePicUrl = new ObservableField<>();

    public SectionViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
        //set trip change to false in case user left without going back
        getDataManager().setTripCrreated(false);
    }

    public void logout() {
        //Todo::change loading for a snackbar to notify successfull logout
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doLogoutRequest()
                .doOnSuccess(logoutResponse ->
                    getDataManager().setUserAsLoggedOut())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(logoutResponse -> {
                    setIsLoading(false);
                    getNavigator().openLoginActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    /*
    Set nav header values
     */
    public void onNavMenuCreated(){
        final String currentUsername = getDataManager().getCurrentUserName();
        if (!TextUtils.isEmpty(currentUsername)){
            userName.set(currentUsername);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getProfilePicturePath();
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl);
        }

    }

}
