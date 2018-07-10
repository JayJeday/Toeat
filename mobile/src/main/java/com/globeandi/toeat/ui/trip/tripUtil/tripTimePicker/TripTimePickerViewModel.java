package com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker;

import android.databinding.ObservableInt;
import android.widget.TimePicker;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.Calendar;

public class TripTimePickerViewModel extends BaseViewModel<TripTimePickerNavigator> {

    public final ObservableInt hour = new ObservableInt();
    public final ObservableInt minute = new ObservableInt();


    public TripTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        initTimePicker();
    }

    private void initTimePicker(){
        Calendar currentTime = Calendar.getInstance();
        this.hour.set(currentTime.get(Calendar.HOUR_OF_DAY));
        this.minute.set(currentTime.get(Calendar.MINUTE));

    }

    public void timeChanged(TimePicker view, int hour, int minute){
        this.hour.set(hour);
        this.minute.set(minute);

    }

    public void submitTime(int hour, int minute){
//        String amPm = (hour >= 12? "PM":"AM");
        getNavigator().submitTime(hour, minute);

    }

    public void dismissDialog(){
        getNavigator().dismissTimePicker();
    }
}
