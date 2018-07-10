package com.globeandi.toeat.ui.groups;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;

import com.globeandi.toeat.databinding.FragmentGroupBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.groups.addgroup.GroupCreationActivity;
import com.globeandi.toeat.ui.pager.GroupDividerPagerAdapter;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripFragment;
import com.globeandi.toeat.ui.trip.TripActivity;
import com.globeandi.toeat.util.MessageEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by jay on 3/17/2018.
 */

public class GroupsFragment extends BaseFragment<FragmentGroupBinding, GroupsViewModel> implements GroupItemNavigator, GroupsNavigator {

    public static final String TAG = GroupsFragment.class.getSimpleName();

    @Inject
    GroupsAdapter mGroupsAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    GroupDividerPagerAdapter mGroupDividerPagerAdapter;

    FragmentGroupBinding mFragmentGroupBinding;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private GroupsViewModel mGroupViewModel;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        GroupsFragment fragment = new GroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    public void changeAlertEvent(MessageEvents events) {
        if (events.isChangeAlert()) {
            mGroupViewModel.fetchGroups();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_group;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //un register the events this was the earliest we can un-register because of the back stack
        EventBus.getDefault().unregister(this);
    }

    @Override
    public GroupsViewModel getViewModel() {
        //request a viewModel of type GroupsViewModel
        mGroupViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GroupsViewModel.class);
        return mGroupViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGroupViewModel.setNavigator(this);
        mGroupsAdapter.setGroupItemNavigator(this);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);

        Log.d(TAG, "onCreate: is called");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.group_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.groupItemAdd:
                startActivity(GroupCreationActivity.newIntent(getActivity()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentGroupBinding = getViewDataBinding();
        setUpRecycleView();
        Log.d(TAG, "onViewCreated was called");
        //subscribe data here
        subscribeToLiveData();
        setupPager();


    }

    private void setUpRecycleView() {
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentGroupBinding.groupRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentGroupBinding.groupRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentGroupBinding.groupRecyclerView.setAdapter(mGroupsAdapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }


    private void subscribeToLiveData() {
        //mGroupViewModel.getOpenGroupDetailsEvent().observe(this, this::openGroupDetails);

        mGroupViewModel.getNewGroupEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                addNewGroup();
            }
        });

        //called when data changes, add mutable live data to list of items
        //TODO::change it to list adapter
        mGroupViewModel.getGroupListLiveData().observe(this, groups -> mGroupViewModel.addGroupItemsToList(groups));
    }

    @Override
    public void openGroupDetails(Long id, Boolean admin) {
        //Todo:: implements master detail layout
        startActivity(TripActivity.newIntent(getActivity())
                .putExtra(TripActivity.EXTRA_GROUP_ID, id)
                .putExtra(TripActivity.EXTRA_GROUP_IS_ADMIN,admin));

    }

    private void setupPager() {

        mGroupDividerPagerAdapter.setCount(2);


        mFragmentGroupBinding.groupDividerViewPager.setAdapter(mGroupDividerPagerAdapter);

        //create tabs for pager
        mFragmentGroupBinding.tabLayout.addTab(mFragmentGroupBinding.tabLayout.newTab().setText(getString(R.string.upcoming_trips)));
        mFragmentGroupBinding.tabLayout.addTab( mFragmentGroupBinding.tabLayout.newTab().setText(getString(R.string.trip_history)));
        mFragmentGroupBinding.groupDividerViewPager.setOffscreenPageLimit( mFragmentGroupBinding.tabLayout.getTabCount());

        //add listener when page change
        mFragmentGroupBinding.groupDividerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( mFragmentGroupBinding.tabLayout));

        mFragmentGroupBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentGroupBinding.groupDividerViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewGroup() {
        //requests if changes were made, if so load the list on  postResume
        getBaseActivity().startActivity(GroupCreationActivity.newIntent(getActivity()));
    }
}
