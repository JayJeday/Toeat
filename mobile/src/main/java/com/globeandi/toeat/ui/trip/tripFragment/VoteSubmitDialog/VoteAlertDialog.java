package com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.DialogVoteAlertBinding;
import com.globeandi.toeat.ui.base.BaseDialog;
import com.globeandi.toeat.util.MessageEvents;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class VoteAlertDialog extends BaseDialog implements VoteAlertCallback {

    private static final String TAG = VoteAlertDialog.class.getSimpleName();

    public static final String ARGUMENT_PLACE_ID = "PLACE_ID";

    public static final String ARGUMENT_PLACE_NAME = "PLACE_NAME";

    public static final String ARGUMENT_GROUP_ID = "GROUP_ID";

    public static final String ARGUMENT_TRIP_ID = "TRIP_ID";

    @Inject
    VoteAlertViewModel mVoteAlertViewModel;

    public static VoteAlertDialog newInstance(String placeName, long placeId, long groupId, long tripId) {
        //pass place object
        Bundle args = new Bundle();
        args.putLong(ARGUMENT_PLACE_ID, placeId);
        args.putString(ARGUMENT_PLACE_NAME, placeName);
        args.putLong(ARGUMENT_GROUP_ID, groupId);
        args.putLong(ARGUMENT_TRIP_ID, tripId);

        VoteAlertDialog fragment = new VoteAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogVoteAlertBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_vote_alert, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mVoteAlertViewModel);

        mVoteAlertViewModel.setNavigator(this);

        String placeName = getArguments().getString(ARGUMENT_PLACE_NAME);
        //string formatted
        String msg = getString(R.string.dialog_msg, placeName);
        binding.dialogMsg.setText(msg);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialogOnCancel() {
        //clear checkbox and send that checkbox was canceled
        //TODO::FIx bugs on Back checkbox is still displayed
        MessageEvents events = new MessageEvents();
        events.setVoteCanceled(true);
        EventBus.getDefault().post(events);
        dismissDialog(TAG);
    }

    @Override
    public void dismissDialogOnSubmit() {
        dismissDialog(TAG);
    }

    @Override
    public void submitVote() {
        //send flags to change view
        //TODO::check if there is problem when changing orientation
        long group_id = getArguments().getLong(ARGUMENT_GROUP_ID);
        long trip_id = getArguments().getLong(ARGUMENT_TRIP_ID);
        long place_id = getArguments().getLong(ARGUMENT_PLACE_ID);

        mVoteAlertViewModel.submitVote(group_id, trip_id, place_id);
        //user vote successfully
        MessageEvents events = new MessageEvents();
        events.setVoteSubmited(true);
        EventBus.getDefault().post(events);
        dismissDialog(TAG);

    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
