package com.globeandi.toeat.ui.login;

/**
 * Created by jay on 3/18/2018.
 * This Interface declare the navigable activities that can be navigable on this activity and the actions
 */

public interface LoginNavigator {

    /*

     */
    void login();

    /*
        if credentials are valid open Section Activity
     */
    void openSectionActivity();

    /*

     */
    void openRegistrationActivity();

    void handleError(Throwable throwable);
}
