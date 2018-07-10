package com.globeandi.toeat.data.local.prefs;

import com.globeandi.toeat.data.DataManager;

/**
 * Created by jay on 3/20/2018.
 */

public interface PreferencesHelper {

    void setProfilePicturePath(String path);

    String getProfilePicturePath();

    void setAccessToken(String accessToken);

    String getAccessToken();

    void setRefreshToken(String refreshToken);

    String getRefreshToken();

    void setCurrentUserEmail(String email);

    String getCurrentUserEmail();

    void setCurrentUserName(String username);

    String getCurrentUserName();

    void setCurrentUserId(Long userId);

    Long getCurrentUserId();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    int getCurrentUserLoggedInMode();

    boolean isTripCreated();

    void setTripCrreated(boolean created);
}
