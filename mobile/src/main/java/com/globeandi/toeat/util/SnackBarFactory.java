package com.globeandi.toeat.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.globeandi.toeat.R;

public class SnackBarFactory {

    /*
    Snack bar  for network is not found
     */
    public static Snackbar createNetworkRetrySnackBar(Context context, View view, String message, View.OnClickListener listener){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        snackbar.setAction("Retry",listener);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        return snackbar;
    }

    /*
    Snack bar for user that are not in trip but vote
     */
    public static Snackbar createNotVoteSnackBar(Context context, View view, String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        return snackbar;
    }


}
