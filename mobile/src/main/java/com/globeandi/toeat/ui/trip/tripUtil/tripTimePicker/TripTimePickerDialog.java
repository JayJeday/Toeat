package com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker;

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
import com.globeandi.toeat.databinding.DialogTripDatePickerBinding;
import com.globeandi.toeat.databinding.DialogTripTimePickerBinding;
import com.globeandi.toeat.ui.base.BaseDialog;
import com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker.TripDatePickerDialog;
import com.globeandi.toeat.util.MessageEvents;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TripTimePickerDialog extends BaseDialog implements TripTimePickerNavigator {

    private static final String TAG = TripDatePickerDialog.class.getSimpleName();

    @Inject
    TripTimePickerViewModel mTripTimePickerViewModel;

    public static TripTimePickerDialog newInstance() {
        Bundle args = new Bundle();
        TripTimePickerDialog fragment = new TripTimePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogTripTimePickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_trip_time_picker, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setTimePicker(mTripTimePickerViewModel);
        mTripTimePickerViewModel.setNavigator(this);
        return view;
    }


    @Override
    public void dismissTimePicker() {
        dismissDialog(TAG);
    }

    @Override
    public void submitTime(int hour, int time) {
        String selectedTime = hour + ":" + time ;

        MessageEvents timeEvent = new MessageEvents();
        timeEvent.setDialogTimeResult(selectedTime);
        EventBus.getDefault().post(timeEvent);

        dismissDialog(TAG);
    }

}
