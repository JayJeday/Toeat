package com.globeandi.toeat.util.rcSelection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import androidx.recyclerview.selection.ItemDetailsLookup;

public class PlaceDetail extends ItemDetailsLookup.ItemDetails<Long> {

    long position;

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return (int)position;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        return position;
    }

    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
        return true;
    }
}
