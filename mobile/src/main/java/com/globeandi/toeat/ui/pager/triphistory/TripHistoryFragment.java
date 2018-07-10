package com.globeandi.toeat.ui.pager.triphistory;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.FragmentHistoryTripsBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.pager.TripHistoryNavigator;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripFragmentViewModel;

import javax.inject.Inject;


public class TripHistoryFragment extends BaseFragment<FragmentHistoryTripsBinding, TripHistoryViewModel> implements TripHistoryNavigator {

    public static final String TAG = TripHistoryFragment.class.getSimpleName();

    @Inject
    TripHistoryAdapter mTripHistoryAdapter;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    FragmentHistoryTripsBinding mHistoryTripsBinding;

    private TripHistoryViewModel mTripHistoryViewModel;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        TripHistoryFragment fragment = new TripHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_trips;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTripHistoryViewModel.setNavigator(this);
        Log.d(TAG, "onCreate was called");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHistoryTripsBinding = getViewDataBinding();
        setUpRecycleView();
        subscribeToLiveData();
        Log.d(TAG, "onViewCreated was called");

    }

    private void subscribeToLiveData(){
        //call this method when data changes
        mTripHistoryViewModel.getTripLiveData().observe(this,trips -> {mTripHistoryViewModel.addTripItems(trips);});
    }

    private void setUpRecycleView() {

        //weak reference to linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHistoryTripsBinding.rvHistoryTrips.setLayoutManager(mLayoutManager);
        mHistoryTripsBinding.rvHistoryTrips.setItemAnimator(new DefaultItemAnimator());
        mHistoryTripsBinding.rvHistoryTrips.setAdapter(mTripHistoryAdapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public TripHistoryViewModel getViewModel() {
        mTripHistoryViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TripHistoryViewModel.class);
        return mTripHistoryViewModel;
    }

    /*
  test pager
   */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onViewCreated was called");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView was called");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivateCreated was called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume was called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause was called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart was called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy was called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach was called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView was called");
    }

    @Override
    public void handleErrors(Throwable throwable) {

    }
}
