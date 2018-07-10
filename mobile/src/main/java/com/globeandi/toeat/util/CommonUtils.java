package com.globeandi.toeat.util;

import android.util.Patterns;


public final class CommonUtils {

    private CommonUtils(){
        //this class is not to be instantiated
    }


    /*
    Validate email input
     */
    public static boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



}
