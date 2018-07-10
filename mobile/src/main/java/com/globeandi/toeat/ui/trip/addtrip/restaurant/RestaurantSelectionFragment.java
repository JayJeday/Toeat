package com.globeandi.toeat.ui.trip.addtrip.restaurant;


import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import android.view.View;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.databinding.FragmentRestaurantSelectionBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.custom.WrapContentLinearLayoutManager;

import com.globeandi.toeat.util.AppConstants;
import com.globeandi.toeat.util.MessageEvents;
import com.globeandi.toeat.util.rcSelection.PlaceKeyProvider;
import com.globeandi.toeat.util.rcSelection.PlaceLookup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;

public class RestaurantSelectionFragment extends BaseFragment<FragmentRestaurantSelectionBinding, RestaurantSelectionViewModel> implements RestaurantSelectionNavigator {

    private static final String TAG = RestaurantSelectionFragment.class.getSimpleName();

    FragmentRestaurantSelectionBinding mRestaurantSelectionBinding;

    @Inject
    Adapter mRestaurantAdapter;


    private SelectionTracker<Long> selectionTracker;

    public static final String ARGUMENT_VOTING_MODE = "VOTING_MODE";


    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    WrapContentLinearLayoutManager mLayoutManager;

    @Inject
    FusedLocationProviderClient mFusedLocationClient;

    private RestaurantSelectionViewModel mSelectionViewModel;

    public static Fragment newInstance(boolean isVotingMode) {
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_VOTING_MODE, isVotingMode);
        Fragment fragment = new RestaurantSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_restaurant_selection;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RestaurantSelectionViewModel getViewModel() {
        mSelectionViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RestaurantSelectionViewModel.class);
        return mSelectionViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectionViewModel.setNavigator(this);
        //need to get last location  and permission
        getLastLocation();
        //get restaurants by default if user already accepted
        if (getBaseActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && mSelectionViewModel.isVotingPhase.get()) {
            //need to pass the aprox current location
            mSelectionViewModel.searchNearbyRestaurant();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.PERM_REQUEST_ACCESS_COARSE_LOCATION);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRestaurantSelectionBinding = getViewDataBinding();
        setupRecycleView();
        manageSelectionTracker();
        subscribeToLiveData();
        mSelectionViewModel.isVotingPhase.set(getArguments().getBoolean(ARGUMENT_VOTING_MODE));
    }

    private void setupRecycleView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRestaurantSelectionBinding.rvPlaces.setLayoutManager(mLayoutManager);
        mRestaurantSelectionBinding.rvPlaces.setItemAnimator(new DefaultItemAnimator());
        mRestaurantSelectionBinding.rvPlaces.setAdapter(mRestaurantAdapter);
    }

    private void manageSelectionTracker() {
        //TODO:: manage dependencies injection
        selectionTracker = new SelectionTracker.Builder<>(

                "my_selection",

                mRestaurantSelectionBinding.rvPlaces,

                //install an OnChildAttachStateChangeListener in the recycle view
                new PlaceKeyProvider(mRestaurantSelectionBinding.rvPlaces.getAdapter()),

                new PlaceLookup(mRestaurantSelectionBinding.rvPlaces),

                StorageStrategy.createLongStorage())

                .withSelectionPredicate(new Adapter.Predicate())

                .build();

        mRestaurantAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {

            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                Long currentKey = (Long) key;
                if (selectionTracker.getSelection().size() == 1 && mSelectionViewModel.isVotingPhase.get()) {
                    PlacesApiResponse.Place place = mSelectionViewModel.mPlaceObservableList.get(currentKey.intValue());
                }
            }

            @Override
            public void onSelectionRefresh() {
                super.onSelectionRefresh();

            }

            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();

                //TODO::************* Fix major bug -> updating list ****************
                //first selection
                if (selectionTracker.getSelection().size() == 1 && !mSelectionViewModel.isVotingPhase.get()) {

                    //we only need the first key (place selected)
                    Long placeIndex = (Long) selectionTracker.getSelection().iterator().next();
                    PlacesApiResponse.Place place = mSelectionViewModel.mPlaceObservableList.get(placeIndex.intValue());

                    List<PlacesApiResponse.Place> selectedPlace = new ArrayList<>();
                    selectedPlace.add(place);
                    //update live data
                    //mSelectionViewModel.getPlaceListLiveData().setValue(selectedPlace);

                    tripSelected(selectedPlace);

                } else if (selectionTracker.getSelection().size() == 4 && mSelectionViewModel.isVotingPhase.get()) {

                    List<PlacesApiResponse.Place> possiblePlaces = new ArrayList<>();
                    Iterator<Long> indexes = selectionTracker.getSelection().iterator();

                    while (indexes.hasNext()) {
                        PlacesApiResponse.Place place = mSelectionViewModel.mPlaceObservableList.get(indexes.next().intValue());
                        possiblePlaces.add(place);
                    }
                    tripSelected(possiblePlaces);
                }
            }
        });
    }


    /*
    close and send back selected places
     */
    private void tripSelected(List<PlacesApiResponse.Place> places) {

        MessageEvents events = new MessageEvents();
        events.setPlaces(places);
        EventBus.getDefault().post(events);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void searchRestaurantByName() {
        String restaurantName = mRestaurantSelectionBinding.etSearchRestaurant.getText().toString();
        if (!TextUtils.isEmpty(restaurantName)) {
            if (getBaseActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //need to pass the aprox current location
                mSelectionViewModel.searchRestaurantByName(restaurantName);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        AppConstants.PERM_REQUEST_ACCESS_COARSE_LOCATION);
            }

        }
    }

    @Override
    public void searchNearbyRestaurant() {
        if (getBaseActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //need to pass the aprox current location
            mSelectionViewModel.searchNearbyRestaurant();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.PERM_REQUEST_ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void goBack() {
        getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.PERM_REQUEST_ACCESS_COARSE_LOCATION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && !mSelectionViewModel.isVotingPhase.get()) {
                    //Permission granted and select one from the list
                    getLastLocation();
                    searchRestaurantByName();
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && mSelectionViewModel.isVotingPhase.get()) {
                    //do a nearby search name
                    getLastLocation();
                    mSelectionViewModel.searchNearbyRestaurant();
                } else {
                    //permission denied
                    Toast.makeText(getBaseActivity(), "Cant access functionality until permission is accepted", Toast.LENGTH_SHORT).show();
                    //make btn nearby visible
                    if (mSelectionViewModel.isVotingPhase.get()) {
                        mRestaurantSelectionBinding.btnSearchNearbyRestaurant.setVisibility(View.VISIBLE);
                    }
                }

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRestaurantSelectionBinding.btnSearchNearbyRestaurant.getVisibility() == View.VISIBLE) {
            mRestaurantSelectionBinding.btnSearchNearbyRestaurant.setVisibility(View.GONE);
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.PERM_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            //if user granted permission
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getBaseActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                double lat = location.getLatitude();
                                double lon = location.getLongitude();
                                //cast
                                mSelectionViewModel.latAndLong.set(lat + "," + lon);
                            }
                        }
                    });
        }

    }

    private void subscribeToLiveData() {
        mSelectionViewModel.getPlaceListLiveData().observe(this, places -> mSelectionViewModel.addPlacesItemsToList(places));
    }
}
