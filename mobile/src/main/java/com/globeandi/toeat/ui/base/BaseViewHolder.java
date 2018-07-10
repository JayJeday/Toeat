package com.globeandi.toeat.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    /*
    Bind item view model with the current item in the list
     */
    public abstract void onBind(int position);
}
