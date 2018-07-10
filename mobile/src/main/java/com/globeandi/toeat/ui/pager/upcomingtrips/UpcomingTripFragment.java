package com.globeandi.toeat.ui.pager.upcomingtrips;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.FragmentUpcomingTripsBinding;
import com.globeandi.toeat.ui.base.BaseFragment;

import javax.inject.Inject;

public class UpcomingTripFragment extends BaseFragment<FragmentUpcomingTripsBinding,UpcomingTripFragmentViewModel> implements UpcomingTripsNavigator {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    UpcomingTripAdapter mUpcomingTripAdapter;

    private UpcomingTripFragmentViewModel mTripFragmentViewModel;

    FragmentUpcomingTripsBinding mUpcomingTripsBinding;


    public static UpcomingTripFragment newInstance() {
        Bundle args = new Bundle();
        UpcomingTripFragment fragment = new UpcomingTripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_upcoming_trips;
    }

    @Override
    public int getBindingVariable() {
       return BR.viewModel;
    }

    @Override
    public UpcomingTripFragmentViewModel getViewModel() {
        mTripFragmentViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpcomingTripFragmentViewModel.class);
        return mTripFragmentViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        //fix for now fetch on change
        if (mTripFragmentViewModel.isTripCreated())
            //fetch upcoming trips
       mTripFragmentViewModel.fetchUpcomingTrips();
        //set created false after fetch
        mTripFragmentViewModel.setTripCreated();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUpcomingTripsBinding = getViewDataBinding();
        setUpRecycleView();
        subscribeToLiveData();
    }

    public void fetchUpcomingTrip(){
        getViewModel().fetchUpcomingTrips();
    }
    private void subscribeToLiveData(){
       mTripFragmentViewModel.getUpcomingTripLiveData().observe(this,trips -> mTripFragmentViewModel.addTripItems(trips));
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mUpcomingTripsBinding.rvUpcomingTrips.setLayoutManager(layoutManager);
        mUpcomingTripsBinding.rvUpcomingTrips.setItemAnimator(new DefaultItemAnimator());
        mUpcomingTripsBinding.rvUpcomingTrips.setAdapter(mUpcomingTripAdapter);
    }

    @Override
    public void handleErrors(Throwable throwable) {

    }
}
