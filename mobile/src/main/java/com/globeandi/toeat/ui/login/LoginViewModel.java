package com.globeandi.toeat.ui.login;

import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.LoginRequest;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.CommonUtils;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jay on 3/17/2018.
 * Logic for the Activity goes in here
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private static final String TAG = "LoginViewModel";

    public LoginViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
    }

    public Set<String> loginFieldsValidation(String email, String password) {
        Set<String> failuresFields = new HashSet<>();
        if (TextUtils.isEmpty(email) || !CommonUtils.isEmailValid(email)) {
            failuresFields.add("email");
        }
        if (TextUtils.isEmpty(password)) {
            failuresFields.add("password");
        }
        return failuresFields;
    }

    public void login(String email, String password, Group group) {
        LoginRequest.ServerLoginRequest loginRequest = new LoginRequest.ServerLoginRequest(email, password);
        group.setVisibility(View.GONE);
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerLoginRequest(loginRequest)
                .doOnSuccess(loginResponse -> {
                            if (loginResponse != null && loginResponse.getUser() != null) {
                                getDataManager().updateUserSection(
                                        loginResponse.getAuthResponse().getAccessToken(),
                                        loginResponse.getAuthResponse().getRefreshToken(),
                                        DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                        loginResponse.getUser().getName(),
                                        loginResponse.getUser().getEmail(),
                                        loginResponse.getUser().getId(),
                                        loginResponse.getUser().getProfile_picture());
                            }
                        }
                )
                //make the operation on an outside thread asynchronous
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(loginResponse -> {
                    setIsLoading(false);
                    getNavigator().openSectionActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                    group.setVisibility(View.VISIBLE);
                }));
    }

    public void onGoogleLoginClick() {

    }

    public void onFacebookLoginClick() {

    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void onClickDisplayRegistration() {
        getNavigator().openRegistrationActivity();
    }
}
