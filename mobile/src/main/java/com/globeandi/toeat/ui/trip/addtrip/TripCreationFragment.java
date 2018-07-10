package com.globeandi.toeat.ui.trip.addtrip;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;

import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.databinding.FragmentTripCreateBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.trip.addtrip.restaurant.RestaurantSelectionFragment;
import com.globeandi.toeat.ui.trip.tripFragment.TripFragment;
import com.globeandi.toeat.ui.trip.tripUtil.tripDatePicker.TripDatePickerDialog;
import com.globeandi.toeat.ui.trip.tripUtil.tripTimePicker.TripTimePickerDialog;
import com.globeandi.toeat.util.ActivityUtils;
import com.globeandi.toeat.util.GlobeDateUtils;
import com.globeandi.toeat.util.MessageEvents;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import javax.inject.Inject;

public class TripCreationFragment extends BaseFragment<FragmentTripCreateBinding, TripCreationViewModel> implements TripCreationNavigation, OnMapReadyCallback, SelectedRestaurantItemAction {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private TripCreationViewModel mTripCreationViewModel;

    private GoogleMap mMap;

    FragmentTripCreateBinding mFragmentTripCreateBinding;

    @Inject
    SelectedRestaurantAdapter mSelectedRestaurantAdapter;

    private Long groupId;

    public static TripCreationFragment newInstance(Long id) {
        Bundle args = new Bundle();
        args.putLong(TripFragment.ARGUMENT_GROUP_ID, id);
        TripCreationFragment fragment = new TripCreationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trip_create;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public TripCreationViewModel getViewModel() {
        mTripCreationViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TripCreationViewModel.class);
        return mTripCreationViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTripCreationViewModel.setNavigator(this);
        EventBus.getDefault().register(this);
        mSelectedRestaurantAdapter.setSelectedRestaurantItemAction(this);
        getBaseActivity().getSupportActionBar().hide();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTripCreateBinding = getViewDataBinding();
        subscribetoLiveData();
        setupRecycleView();

        //Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get group id when fragment is created
        //TODO set this in the viewModel
        groupId = (Long) (getArguments() != null ? getArguments().get(TripFragment.ARGUMENT_GROUP_ID) : null);
        textWatcherListener();

        mFragmentTripCreateBinding.swCarpool.setOnCheckedChangeListener(
                (x, y) -> Toast.makeText(getBaseActivity(), "Functionality not available in version Beta", Toast.LENGTH_SHORT).show());
    }

    private void setupRecycleView() {
        //weak reference for issue between fragment, an instance of layout manager exist in memory because of dagger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentTripCreateBinding.rvSelectedPlaces.setLayoutManager(linearLayoutManager);

        mFragmentTripCreateBinding.rvSelectedPlaces.setItemAnimator(new DefaultItemAnimator());
        mFragmentTripCreateBinding.rvSelectedPlaces.setAdapter(mSelectedRestaurantAdapter);
    }

    //TODO verify if usage work
    private void subscribetoLiveData() {
        mTripCreationViewModel.liveItemList.observe(this, places -> {
            mTripCreationViewModel.addSelectedPlaces(places);
        });
    }

    @SuppressLint("CheckResult")
    private void textWatcherListener() {
        //text watcher for trip title
        EditText tripTitleEt = mFragmentTripCreateBinding.tripName;
        RxTextView.textChanges(tripTitleEt).subscribe(text -> {
            mFragmentTripCreateBinding.tvTripTitleCounter.setText(String.valueOf(text.length()));

            if (text.length() == 20) {
                mFragmentTripCreateBinding.lTripNameCounter.setBackground(ContextCompat.getDrawable(getBaseActivity(), R.drawable.counter_limit_bg));
            } else {
                mFragmentTripCreateBinding.lTripNameCounter.setBackground(ContextCompat.getDrawable(getBaseActivity(), R.drawable.counter_normal_bg));
            }
        });

    }

    /*
    Event bus subscriber
     */
    @Subscribe
    public void tripDialogResult(MessageEvents events) {
        if (events.getDialogDateResult() != null) {
            mTripCreationViewModel.datePickerResult.set(events.getDialogDateResult());
            mFragmentTripCreateBinding.tvDate.setText(GlobeDateUtils.formatDateToDisplay(mTripCreationViewModel.datePickerResult.get()));
        }
        if (events.getDialogTimeResult() != null) {
            String time = events.getDialogTimeResult();
            mTripCreationViewModel.timePickerResult.set(GlobeDateUtils.formatTimeToDisplay(time));
            mFragmentTripCreateBinding.tvTime.setText(mTripCreationViewModel.timePickerResult.get());
        }
        if (events.getPlaces() != null) {
            //show map because we have places selected
            mTripCreationViewModel.liveItemList.setValue(events.getPlaces());
            mTripCreationViewModel.isMapSelected.set(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void createTrip() {
        String tripTitle = mFragmentTripCreateBinding.tripName.getText().toString();
        boolean isCarpoled = mFragmentTripCreateBinding.swCarpool.getShowText();
        TripResponse.Trip trip = mTripCreationViewModel.setUpTrip(tripTitle, isCarpoled);

        clearValidationTv();

        Set<String> failureFields = mTripCreationViewModel.tripCreatioonFieldsValidation(tripTitle,
                mTripCreationViewModel.datePickerResult.get(),
                mTripCreationViewModel.timePickerResult.get());

        if (failureFields.isEmpty()) {

            mTripCreationViewModel.createTrip(groupId, trip);

        } else {
            for (String field : failureFields) {
                switch (field) {
                    case "title":
                        mFragmentTripCreateBinding.ivTitleAlert.setVisibility(View.VISIBLE);
                        break;
                    case "date":
                        mFragmentTripCreateBinding.ivDateAlert.setVisibility(View.VISIBLE);
                        break;
                    case "time":
                        mFragmentTripCreateBinding.ivTimeAlert.setVisibility(View.VISIBLE);
                        break;
                    case "places":
                        mFragmentTripCreateBinding.ivPlacesAlert.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    private void clearValidationTv() {
        mFragmentTripCreateBinding.ivPlacesAlert.setVisibility(View.GONE);
        mFragmentTripCreateBinding.ivTitleAlert.setVisibility(View.GONE);
        mFragmentTripCreateBinding.ivDateAlert.setVisibility(View.GONE);
        mFragmentTripCreateBinding.ivTimeAlert.setVisibility(View.GONE);
    }

    @Override
    public void openTripDatePicker() {
        TripDatePickerDialog.newInstance().show(getBaseActivity().getSupportFragmentManager());
    }

    @Override
    public void openTripTimePicker() {
        TripTimePickerDialog.newInstance().show(getBaseActivity().getSupportFragmentManager());
    }

    @Override
    public void alertChangesEvent() {
        MessageEvents events = new MessageEvents();
        events.setChangeAlert(true);
        EventBus.getDefault().post(events);
    }

    @Override
    public void openTripRestaurantSelector() {
        ActivityUtils.replaceFragmentInActivity(getBaseActivity().getSupportFragmentManager(), RestaurantSelectionFragment.newInstance(mTripCreationViewModel.votingPhase.get()));
    }

    @Override
    public void goBack() {
        getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getBaseActivity());
        this.mMap = googleMap;

    }

    /*
    Set map with the selected views
     */
    private void showDirectionOnSelectPlaces(double longitude, double latitude) {
        //clear all maps direction marker and stuff
        mMap.clear();
        LatLng tripLocation = new LatLng(latitude, longitude);
        CameraPosition cameraView = CameraPosition.builder().target(tripLocation).zoom(16).bearing(0)
                .tilt(45).build();
        this.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraView));
        this.mMap.addMarker(new MarkerOptions().position(tripLocation));
    }

    @Override
    public void onPlaceSelected(PlacesApiResponse.Place place) {
        double lat = place.getGeometry().getLocation().getLatitude();
        double lon = place.getGeometry().getLocation().getLongitude();

        showDirectionOnSelectPlaces(lon, lat);

    }

}
