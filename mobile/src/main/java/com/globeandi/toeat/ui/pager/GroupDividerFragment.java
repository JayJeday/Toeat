package com.globeandi.toeat.ui.pager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.FragmentGroupdividerBinding;

import com.globeandi.toeat.ui.base.BaseFragment;

import javax.inject.Inject;


public class GroupDividerFragment extends BaseFragment<FragmentGroupdividerBinding, GroupDividerViewModel> {

    public static final String TAG = GroupDividerFragment.class.getSimpleName();

    @Inject
    GroupDividerViewModel mGroupDividerViewModel;

    @Inject
    GroupDividerPagerAdapter mGroupDividerPagerAdapter;

    private FragmentGroupdividerBinding mFragmentGroupdividerBinding;


    public static GroupDividerFragment newInstance() {
        Bundle args = new Bundle();
        GroupDividerFragment fragment = new GroupDividerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_groupdivider;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentGroupdividerBinding = getViewDataBinding();
        setupPager();
    }

    private void setupPager() {

        mGroupDividerPagerAdapter.setCount(2);

        mFragmentGroupdividerBinding.groupDividerViewPager.setAdapter(mGroupDividerPagerAdapter);

        //create tabs for pager
        mFragmentGroupdividerBinding.tabLayout.addTab(mFragmentGroupdividerBinding.tabLayout.newTab().setText(getString(R.string.groups)));
        mFragmentGroupdividerBinding.tabLayout.addTab(mFragmentGroupdividerBinding.tabLayout.newTab().setText(getString(R.string.trip_history)));

        mFragmentGroupdividerBinding.groupDividerViewPager.setOffscreenPageLimit(mFragmentGroupdividerBinding.tabLayout.getTabCount());
        //add listener when page change
        mFragmentGroupdividerBinding.groupDividerViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mFragmentGroupdividerBinding.tabLayout));

        mFragmentGroupdividerBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentGroupdividerBinding.groupDividerViewPager.setCurrentItem(tab.getPosition());

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
    public GroupDividerViewModel getViewModel() {
        return mGroupDividerViewModel;
    }

}
