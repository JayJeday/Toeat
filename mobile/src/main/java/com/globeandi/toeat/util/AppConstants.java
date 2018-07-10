package com.globeandi.toeat.util;

/**
 * Created by jay on 3/16/2018.
 * Store all the constant configurable in the app
 */

public final class AppConstants {

    public static final String DB_NAME = "toeat.db";

    public static final String INTERNAL_IMAGE_DIRECTORY = "profilePic";

    public static final int PERM_REQUEST_READ_EXTERNAL_STORAGE = 1;

    public static final int PERM_REQUEST_ACCESS_COARSE_LOCATION = 2;

    public static final String PREF_NAME = "toeat_pref";

    //****** network url constant ************
    //development environment
    public static final String DEVELOPMENT_BASE_URL = "http://10.0.2.2:80";

    //productions url
    public static final String GOOGLE_PLACES_BASE_URL_API = "https://maps.googleapis.com/maps/";

    public static final String TOEAT_URL_API = "http://www.globeandi.com/";


    private AppConstants(){
        //cannot be instantiated
    }
}
