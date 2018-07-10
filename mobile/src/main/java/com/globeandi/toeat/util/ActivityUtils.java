package com.globeandi.toeat.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.globeandi.toeat.R;



public final class ActivityUtils {

    private ActivityUtils(){

    }

    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }


    /*
    Replace fragment and added to the back stack
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //get current fragment
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.tripContainer);
        if (currentFragment != null ){
            transaction.replace(R.id.tripContainer,fragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
