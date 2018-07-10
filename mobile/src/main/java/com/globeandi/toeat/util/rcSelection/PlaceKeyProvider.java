package com.globeandi.toeat.util.rcSelection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import androidx.recyclerview.selection.ItemKeyProvider;

public class PlaceKeyProvider extends ItemKeyProvider<Long>{
    /**
     * Creates a new provider with the given scope.
     *
     * @param scope Scope can't be changed at runtime.
     */
    public PlaceKeyProvider(RecyclerView.Adapter adapter) {
        super(ItemKeyProvider.SCOPE_MAPPED);
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) position;
    }

    @Override
    public int getPosition(@NonNull Long key) {
        long value = key;
        return (int)value;
    }
}
