package com.globeandi.toeat.data;

import com.globeandi.toeat.data.local.db.DbHelper;
import com.globeandi.toeat.data.local.prefs.PreferencesHelper;
import com.globeandi.toeat.data.models.api.LoginResponse;
import com.globeandi.toeat.data.models.api.RegisterResponse;
import com.globeandi.toeat.data.remote.ApiHeader;
import com.globeandi.toeat.data.remote.ApiHelper;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by jay on 3/20/2018.
 *
 * Global calls of data -> mainly login,logout, seeding tables,
 */

public interface DataManager extends DbHelper,PreferencesHelper,ApiHelper {

    void setUserAsLoggedOut();

    void updateUserSection(String accessToken,
                           String refreshToken,
                           LoggedInMode loggedInMode,
                            String username,
                           String email,
                           Long userId,
                           String profilePathPic);

    /*
        Set logging mode
     */
    enum LoggedInMode{

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type){
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

}
