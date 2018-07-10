package com.globeandi.toeat.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.dependencies.PreferenceInfo;

import javax.inject.Inject;

/**
 * Created by jay on 3/20/2018.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    //Keys
    private static final String PREF_KEY_PROFILE_PICTURE = "PREF_KEY_PROFILE_PICTURE";

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN";

    private static final String PREF_KEY_LOGGED_IN_MODE = "PREF_KEY_LOGGED_IN_MODE";

    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";

    private static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";

    private static final String PREF_KEY_TRIP_CREATED = "PREF_KEY_TRIP_CREATED";


    private final SharedPreferences mPreferences;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        this.mPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setProfilePicturePath(String path) {
        this.mPreferences.edit().putString(PREF_KEY_PROFILE_PICTURE, path).apply();
    }

    @Override
    public String getProfilePicturePath() {
        return this.mPreferences.getString(PREF_KEY_PROFILE_PICTURE, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.mPreferences.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getRefreshToken() {
        return this.mPreferences.getString(PREF_KEY_REFRESH_TOKEN, null);
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.mPreferences.edit().putString(PREF_KEY_REFRESH_TOKEN, refreshToken).apply();
    }

    @Override
    public String getAccessToken() {
        return this.mPreferences.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        this.mPreferences.edit().putString(PREF_KEY_USER_EMAIL,email).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return this.mPreferences.getString(PREF_KEY_USER_EMAIL,null);
    }

    @Override
    public void setCurrentUserName(String username) {
        this.mPreferences.edit().putString(PREF_KEY_USER_NAME,username).apply();
    }

    @Override
    public String getCurrentUserName() {
        return this.mPreferences.getString(PREF_KEY_USER_NAME,null);
    }



    @Override
    public Long getCurrentUserId() {
        return this.mPreferences.getLong(PREF_KEY_USER_ID,0);
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
            this.mPreferences.edit().putInt(PREF_KEY_LOGGED_IN_MODE,mode.getType()).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return this.mPreferences.getInt(PREF_KEY_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public boolean isTripCreated() {
        return this.mPreferences.getBoolean(PREF_KEY_TRIP_CREATED,false);
    }

    @Override
    public void setTripCrreated(boolean created) {
        this.mPreferences.edit().putBoolean(PREF_KEY_TRIP_CREATED,created).apply();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        this.mPreferences.edit().putLong(PREF_KEY_USER_ID,userId).apply();
    }
}
