package com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker;

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
import com.globeandi.toeat.ui.base.BaseDialog;
import com.globeandi.toeat.util.MessageEvents;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TripDatePickerDialog extends BaseDialog implements TripDatePickerNavigator {

    private static final String TAG = TripDatePickerDialog.class.getSimpleName();

    @Inject
    TripDatePickerViewModel mTripDatePickerViewModel;

    public static TripDatePickerDialog newInstance() {
        Bundle args = new Bundle();
        TripDatePickerDialog fragment = new TripDatePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogTripDatePickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_trip_date_picker, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setDatePicker(mTripDatePickerViewModel);
        mTripDatePickerViewModel.setNavigator(this);
        return view;
    }

    @Override
    public void dismissTripDatePicker() {
        dismissDialog(TAG);
    }

    @Override
    public void submitTripDate(int day, int month, int year) {
        //format date to string
        String selectedDate = (month+1) + "/" + day + "/" + year;
        MessageEvents events = new MessageEvents();
        events.setDialogDateResult(selectedDate);
        EventBus.getDefault().post(events);
        dismissDialog(TAG);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

}
