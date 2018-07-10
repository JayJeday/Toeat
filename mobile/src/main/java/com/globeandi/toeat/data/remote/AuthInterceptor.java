package com.globeandi.toeat.data.remote;


import com.globeandi.toeat.data.local.prefs.AppPreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthInterceptor implements Interceptor {


    private final AppPreferencesHelper mPreferenceManager;

    @Inject
    public AuthInterceptor(AppPreferencesHelper preferenceManager) {
        mPreferenceManager = preferenceManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("Accept","application/json");
        builder.addHeader("Accept-Encoding", "identity");
        builder.addHeader("Connection","Close");
        builder.addHeader("Content-type","application/json; charset=UTF-8");

        //if user have an access token user is logged In
        if (mPreferenceManager.getAccessToken() != null){
            builder.addHeader("Authorization","Bearer " + mPreferenceManager.getAccessToken());
        }

        originalRequest = builder.build();
        return chain.proceed(originalRequest);
    }
}
