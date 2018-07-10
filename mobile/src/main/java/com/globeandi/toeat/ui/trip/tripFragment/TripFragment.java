package com.globeandi.toeat.ui.trip.tripFragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.databinding.FragmentTripBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.search.SearchActivity;
import com.globeandi.toeat.ui.trip.addtrip.TripCreationFragment;
import com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog.VoteAlertDialog;
import com.globeandi.toeat.util.ActivityUtils;
import com.globeandi.toeat.util.GlobeDateUtils;
import com.globeandi.toeat.util.MessageEvents;
import com.globeandi.toeat.util.SnackBarFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

public class TripFragment extends BaseFragment<FragmentTripBinding, TripFragmentViewModel> implements TripFragmentNavigation, OnMapReadyCallback, TripFragmentVotingItemViewModel.VotingItemListener {

    public static final String TAG = TripFragment.class.getSimpleName();

    public static final String ARGUMENT_GROUP_ID = "GROUP_ID";

    public static final String ARGUMENT_GROUP_IS_ADMIN = "IS_ADMIN";

    FragmentTripBinding mFragmentTripBinding;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    TripFragmentVotingListAdapter mVotingListAdapter;

    @Inject
    UserGoingAdapter mUserGoingAdapter;

    private TripFragmentViewModel mTripFragmentViewModel;

    private GoogleMap mMap;

    public static TripFragment newInstance(Long id, Boolean isAdmin) {
        Bundle args = new Bundle();
        args.putLong(ARGUMENT_GROUP_ID, id);
        args.putBoolean(ARGUMENT_GROUP_IS_ADMIN, isAdmin);
        TripFragment tripFragment = new TripFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTripFragmentViewModel.setNavigator(this);
        mVotingListAdapter.setVotingItemListener(this);

        EventBus.getDefault().register(this);
        getBaseActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        mTripFragmentViewModel.groupId.set((getArguments() != null ? (Long) getArguments().get(ARGUMENT_GROUP_ID) : null));

        mTripFragmentViewModel.isAdmin.set((getArguments() != null ? (Boolean) getArguments().get(ARGUMENT_GROUP_IS_ADMIN) : false));
        //set group id in view model to not lose it in rotation
        //if is empty set empty layout
        mTripFragmentViewModel.fetchActiveTrip(mTripFragmentViewModel.groupId.get());

        getBaseActivity().getWindow().getDecorView().setSystemUiVisibility(0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public TripFragmentViewModel getViewModel() {
        mTripFragmentViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TripFragmentViewModel.class);
        return mTripFragmentViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trip;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTripBinding = getViewDataBinding();
        subscribeLiveData();

        //hide join and un join button if user is admin
//        if(mTripFragmentViewModel.isAdmin.get()){
//            mFragmentTripBinding.tripCreated.btnJoinable.setVisibility(View.GONE);
//            mFragmentTripBinding.tripCreated.btnUnJoin.setVisibility(View.GONE);
//        }

        //get
        //Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapTrip);
        mapFragment.getMapAsync(this);

        setupVotingListRv();
        setupUserGoingRv();
        getBaseActivity().getSupportActionBar().show();

    }

    /*
    Voting list rv
     */
    private void setupVotingListRv() {
        //weak reference linear layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentTripBinding.tripCreated.rvVotingPacesList.setLayoutManager(linearLayoutManager);

        mFragmentTripBinding.tripCreated.rvVotingPacesList.setAdapter(mVotingListAdapter);
    }

    private void setupUserGoingRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentTripBinding.tripCreated.rvUserGoing.setLayoutManager(linearLayoutManager);

        mFragmentTripBinding.tripCreated.rvUserGoing.setAdapter(mUserGoingAdapter);
    }

    private void subscribeLiveData() {
        mTripFragmentViewModel.mTripLiveData.observe(this, trip -> mTripFragmentViewModel.mTrip.set(trip));

     //   mTripFragmentViewModel.placesToEatLiveData.observe(this, placesToEats -> mTripFragmentViewModel.mPlaces.addAll(placesToEats));

     //   mTripFragmentViewModel.userGoingLiveData.observe(this,outings -> mTripFragmentViewModel.mOutingsObservableList.addAll(outings));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trip_menu, menu);

        MenuItem item = menu.findItem(R.id.app_trip_search_action);
//        MenuItem createTripItem = menu.findItem(R.id.createTrip);
        //if user is not admin hide searching option
        if (!mTripFragmentViewModel.isAdmin.get()) {
            item.setVisible(false);
//            createTripItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.leaveGroup:
                //TODO:: if user is admin shows dialog warning that the group will be deleted
              mTripFragmentViewModel.removeUserFromGroup(mTripFragmentViewModel.groupId.get());
                return true;
//            case R.id.createTrip:
//                openTripCreationActivity();
//                return true;
            case R.id.app_trip_search_action:
                Intent intent = new Intent(getBaseActivity(), SearchActivity.class);
                intent.putExtra(SearchActivity.EXTRA_GROUP_ID, mTripFragmentViewModel.groupId.get());
                getBaseActivity().startActivity(intent);
                return true;
            case android.R.id.home:
                getBaseActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    /*
    Event that is executed if user leave group
    temporary fix until Room is implemented
    */
    @Override
    public void alertChangesEvent() {
        MessageEvents events = new MessageEvents();
        events.setChangeAlert(true);
        EventBus.getDefault().post(events);
    }


    @Override
    public void openTripCreationActivity() {
        ActivityUtils.replaceFragmentInActivity(getBaseActivity().getSupportFragmentManager(), TripCreationFragment.newInstance(mTripFragmentViewModel.groupId.get()));
    }

    @Override
    public void onBack() {
        getBaseActivity().finish();
    }


    /*
    Setup views in trip fragment layout
     */
    @Override
    public void setUpViews(TripResponse.Trip trip) {
        mFragmentTripBinding.tripCreated.tvTripDate.setText(GlobeDateUtils.getDateFromServer(trip.getTripDateTime()));
        mFragmentTripBinding.tripCreated.tvTripTime.setText(GlobeDateUtils.getTimeFromServer(trip.getTripDateTime()));
        mFragmentTripBinding.tripCreated.tvIsCarpooled.setText((trip.getCarpool()) ? "Yes" : "No");
        mFragmentTripBinding.tripCreated.tvTripTitle.setText(trip.getTitle());
        mFragmentTripBinding.tripCreated.tvVotingMode.setText((trip.getVoting()) ? "Yes" : "No");
        Group listView = mFragmentTripBinding.tripCreated.gVotedList;
        Group submittedViews = mFragmentTripBinding.tripCreated.submittedVote;
        //get voting mode
        mTripFragmentViewModel.isVotingMode.set(trip.getVoting());

        //setup places  list get place
        //If voting mode is not true show map with the place to go******************
        if (!mTripFragmentViewModel.isVotingMode.get()) {
            //display the map with the place info
            TripResponse.PlacesToEat placesToEat = trip.getPlacesToEats().get(0);
            double longitude = Double.parseDouble(placesToEat.getLongitude());
            double latitude = Double.parseDouble(placesToEat.getLatitude());

            mTripFragmentViewModel.latitud.set(latitude);
            mTripFragmentViewModel.longitud.set(longitude);

            mFragmentTripBinding.tripCreated.tvSelectedPlace.setText(placesToEat.getName());
            mFragmentTripBinding.tripCreated.tvSelectedPlaceVisinity.setText(placesToEat.getVicinity());

            listView.setVisibility(View.GONE);
            submittedViews.setVisibility(View.GONE);

            //** temporal fix set trip completed if time passed
            if (GlobeDateUtils.isTripCompleted(trip.getTripDateTime())){
                mTripFragmentViewModel.completeTrip(trip.getId());
            }

        }else{
            //** temporal fix
            //check if time up in voting
            mVotingListAdapter.submitList(mTripFragmentViewModel.mPlaces);

            if (mTripFragmentViewModel.showResults.get()){
                mTripFragmentViewModel.getResults(mTripFragmentViewModel.mTrip.get().getId());
            }
        }
    }

    /*
    Join and un join actions
     */
    @Override
    public void joinTripAction() {
        long groupId = mTripFragmentViewModel.groupId.get();
        long tripId = mTripFragmentViewModel.mTrip.get().getId();
        mTripFragmentViewModel.joinTrip(groupId, tripId);
    }

    @Override
    public void unJoinTripAction() {
        long groupId = mTripFragmentViewModel.groupId.get();
        long tripId = mTripFragmentViewModel.mTrip.get().getId();
        mTripFragmentViewModel.unJoinTrip(groupId, tripId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());
        this.mMap = googleMap;

        Double latitude = mTripFragmentViewModel.latitud.get();
        Double longitude = mTripFragmentViewModel.longitud.get();
        if (latitude != null && longitude != null){
            showDirectionOnSelectPlaces(longitude,latitude);
        }
    }

    private void showDirectionOnSelectPlaces(double longitude, double latitude) {
        //clear all maps direction marker and stuff
        mMap.clear();
        LatLng tripLocation = new LatLng(latitude, longitude);
        CameraPosition cameraView = CameraPosition.builder().target(tripLocation).zoom(16).bearing(0)
                .tilt(45).build();
        this.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraView));
        this.mMap.addMarker(new MarkerOptions().position(tripLocation));
    }

    //************* Voting *********************
    /*
        Manage checkbox status if user cancel or user submitted
         */
    @Subscribe
    public void changesEvents(MessageEvents events) {
        //get the active group. User created a trip
        if (events.isChangeAlert()) {
            mTripFragmentViewModel.fetchActiveTrip(mTripFragmentViewModel.groupId.get());
        }

        //if user cancel button
        if (events.isVoteCanceled() && mTripFragmentViewModel.checkboxView.get() != null) {
            CheckBox checkBox = (CheckBox) mTripFragmentViewModel.checkboxView.get();

            if (checkBox != null) {
                checkBox.setChecked(false);
            }
        }
        //for the next checkbox
        mTripFragmentViewModel.checkboxView.set(null);
        //if user submit vote, delete list,show submit view
        //TODO animate view here
        if (events.isVoteSubmited()) {
            //hide recyclerView
            Group group = mFragmentTripBinding.tripCreated.gVotedList;
            group.setVisibility(View.GONE);
            //show submit views
            Group submitVote = mFragmentTripBinding.tripCreated.submittedVote;
            submitVote.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onVotingSelected(View view, TripResponse.PlacesToEat place) {
        //set the view in the view model
        mTripFragmentViewModel.checkboxView.set(view);

        //if user has not join a trip deselect the checkbox and show message
        if (!mTripFragmentViewModel.isUserJoined.get()){
            View parent = mFragmentTripBinding.getRoot();
            SnackBarFactory.createNotVoteSnackBar(getBaseActivity(),parent,"Unable to vote until you join the trip").show();
            CheckBox checkBox = (CheckBox) mTripFragmentViewModel.checkboxView.get();
            if (checkBox != null) {
                checkBox.setChecked(false);
            }
        }else{
            String placeNameArg = place.getName();
            long placeId = place.getId();
            long groupId = mTripFragmentViewModel.groupId.get();
            //verify trip
            long tripId = mTripFragmentViewModel.mTrip.get().getId();
            //need to pass the id trip and
            VoteAlertDialog.newInstance(placeNameArg, placeId, groupId, tripId).show(getBaseActivity().getSupportFragmentManager());
        }
    }
}