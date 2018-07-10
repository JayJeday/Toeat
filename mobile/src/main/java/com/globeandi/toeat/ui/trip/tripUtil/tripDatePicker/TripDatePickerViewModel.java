package com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker;

import android.databinding.ObservableInt;
import android.widget.DatePicker;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.time.LocalDate;
import java.util.Calendar;

public class TripDatePickerViewModel extends BaseViewModel<TripDatePickerNavigator> {

    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();


    public TripDatePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
       initDatePicker();

    }

    /*
    Initialize  date picker
     */
    private void initDatePicker(){
        Calendar calendar = Calendar.getInstance();

        year.set(calendar.get(Calendar.YEAR));
        month.set(calendar.get(Calendar.MONTH));
        day.set(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);
    }

    public void dismissDialog(){
        getNavigator().dismissTripDatePicker();
    }

    public void onDateSelected(int day,int month,int year){
        getNavigator().submitTripDate(day, month, year);
    }
}
