package com.globeandi.toeat.util.rcSelection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;


import com.globeandi.toeat.ui.trip.addtrip.restaurant.Adapter;

import androidx.recyclerview.selection.ItemDetailsLookup;

public final class PlaceLookup extends ItemDetailsLookup<Long> {

    private RecyclerView mRecView;

    public PlaceLookup(RecyclerView recyclerView) {
        mRecView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = mRecView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = mRecView.getChildViewHolder(view);
            if (viewHolder instanceof Adapter.PlacesViewHolder) {
                return ((Adapter.PlacesViewHolder) viewHolder).getPlaceDetails();
            }
        }
        return null;
    }
}
