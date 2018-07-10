package com.globeandi.toeat.ui.registration;

import android.support.constraint.Group;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import com.globeandi.toeat.data.DataManager;

import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.CommonUtils;
import com.globeandi.toeat.util.rx.SchedulerProvider;


import java.io.File;
import java.util.HashSet;

import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by jay on 3/21/2018.
 */

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigation> {

    private static final String TAG = "RegistrationViewModel";

    private RequestBody description;
    private MultipartBody.Part body;

    public RegistrationViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
    }

    public void onClickRegister() {
        getNavigator().register();
    }

    public void onClickSetImageProfile() {
        getNavigator().setProfilePicture();
    }

    public Set<String> registrationFieldsValidation(String email, String password, String usernanme) {
        //list that hold witch field is validated
        Set<String> mapValidation = new HashSet<>();
        if (TextUtils.isEmpty(email) || !CommonUtils.isEmailValid(email)) {
            mapValidation.add("email");
        }

        if (TextUtils.isEmpty(usernanme)) {
            mapValidation.add("username");
        }

        if (TextUtils.isEmpty(password)) {
            mapValidation.add("password");
        }
        return mapValidation;
    }

    public void register(RequestBody name, RequestBody email, RequestBody password, Group group) {
      group.setVisibility(View.GONE);
        setIsLoading(true);
        getCompositeDisposable()
                //observer -> consume LoginResponse item
                .add(getDataManager()
                        //call the retrofit method to call  the server
                        .doServerRegisterRequest(name, email, password, description, body)
                        //if call to the server was a success, operate on the consumer of doOnSuccess the return the response
                        .doOnSuccess(response -> {
                                    if (response != null && response.getUser() != null) {
                                        getDataManager().updateUserSection(
                                                response.getAuthResponse().getAccessToken(),
                                                response.getAuthResponse().getRefreshToken(),
                                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                                response.getUser().getName(),
                                                response.getUser().getEmail(),
                                                response.getUser().getId(),
                                                response.getUser().getProfile_picture());
                                    }
                                }
                        )
                        //returns a default, shared Scheduler(thread) instance intended for IO-bound work
                        .subscribeOn(getSchedulerProvider().io())
                        //Subscriber action to execute its results on android main UI thread
                        .observeOn(getSchedulerProvider().ui())
                        //Subscribes to a Single and provides callbacks to handle the item it emits or any error notification it issues
                        .subscribe(response -> {
                            setIsLoading(false);
                            getNavigator().openSectionActivity();
                        }, throwable -> {
                            setIsLoading(false);
                            //manage errors
                            getNavigator().handleError(throwable);
                            group.setVisibility(View.VISIBLE);
                        }));
    }

    public void setupImageToUpload(String path) {

        //save path to Shared preference
        getDataManager().setProfilePicturePath(path);
        //find that image
        File file = new File(path, "profilePicture.jpg");
        //file with any image type
        RequestBody profilePic = RequestBody.create(MediaType.parse("image/*"), file);
        //file part of multi request
        body = MultipartBody.Part.createFormData("profile_pic", file.getName(), profilePic);

        description = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void showMessage(){
        getNavigator().showMessage();
    }
}
