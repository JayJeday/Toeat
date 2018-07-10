package com.globeandi.toeat.data.remote;


import android.util.Log;

import com.globeandi.toeat.data.local.prefs.AppPreferencesHelper;
import com.globeandi.toeat.ui.login.LoginActivity;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/*
This class manage token refreshment
 */
@Singleton
public class AuthAuthenticator implements Authenticator {

    private static final String TAG = "AuthAuthenticator";

    private final AppPreferencesHelper mPreferenceManager;

    private final RefreshTokenService mRefreshTokenService;

    @Inject
    public AuthAuthenticator(AppPreferencesHelper preferenceManager, RefreshTokenService refreshTokenService) {
        this.mPreferenceManager = preferenceManager;
        this.mRefreshTokenService = refreshTokenService;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

//        //with 3 tries return null
        if (responseCount(response) >= 3) {
            return null;
        }
        String refreshToken = mPreferenceManager.getRefreshToken();

//        //we need a Synchronously call here
//        //TODO:: use RxJava to make the call instead  of retrofit

        Call<ApiHeader.ProtectedApiHeader> call = mRefreshTokenService.refresh(refreshToken);
        retrofit2.Response<ApiHeader.ProtectedApiHeader> responseCall = call.execute();

        if (responseCall.isSuccessful()) {
            Log.d(TAG, "response: " + responseCall);
            //TODO:: refactor this to a method
            mPreferenceManager.setAccessToken(responseCall.body().getAccessToken());
            mPreferenceManager.setRefreshToken(responseCall.body().getRefreshToken());

            return response.request().newBuilder().header("Authorization", "Bearer " +
                    responseCall.body().getAccessToken()).build();
        } else {
            return null;
        }

        //update header
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
