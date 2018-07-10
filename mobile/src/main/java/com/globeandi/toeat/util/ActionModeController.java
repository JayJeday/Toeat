package com.globeandi.toeat.util;

import android.content.Context;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.selection.SelectionTracker;

/*
Manage action for state changes in SelectionTracker of recycler view
 */
public class ActionModeController implements ActionMode.Callback {

    private final SelectionTracker<Long> mSelectionTracker;
    private final Context mContext;

    public ActionModeController(SelectionTracker<Long> selectionTracker, Context context) {
        mSelectionTracker = selectionTracker;
        mContext = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mSelectionTracker.clearSelection();
    }
}
