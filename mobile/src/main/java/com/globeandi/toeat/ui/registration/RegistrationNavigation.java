package com.globeandi.toeat.ui.registration;

/**
 * Created by jay on 3/23/2018.
 */

public interface RegistrationNavigation {

    void register();

    void openSectionActivity();

    void handleError(Throwable throwable);

    void setProfilePicture();

    void goBack();

    void showMessage();
}
