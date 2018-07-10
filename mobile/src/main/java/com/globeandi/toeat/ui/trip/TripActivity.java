package com.globeandi.toeat.ui.trip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.ActivityTripBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.ui.trip.tripFragment.TripFragment;
import com.globeandi.toeat.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/*

 */
public class TripActivity extends BaseActivity<ActivityTripBinding,TripViewModel> implements HasSupportFragmentInjector {

    ActivityTripBinding mActivityTripBinding;

    private static final String TAG = "TripActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    public static final String EXTRA_GROUP_ID = "GROUP_ID";

    public static final String EXTRA_GROUP_IS_ADMIN = "IS_ADMIN";

    @Inject
    TripViewModel mTripViewModel;

    private Toolbar mToolbar;

    /*
    get to this activity
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, TripActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public TripViewModel getViewModel() {
        return mTripViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTripBinding = getViewDataBinding();
        //TODO:fix better implementation this
        Fragment tripFragment = createViewFragment();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), tripFragment,R.id.tripContainer);

        //Todo:: find better solution in phase 3 material design
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        //reset light bar status
        getWindow().getDecorView().setSystemUiVisibility(0);
        setToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private Fragment createViewFragment() {
        //get id of the group that was passed by the intent extra
        Long id = getIntent().getLongExtra(EXTRA_GROUP_ID,0);

        Boolean isAdmin = getIntent().getBooleanExtra(EXTRA_GROUP_IS_ADMIN,false);

        //verify if there is a fragment in the container
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.tripContainer);
        if (fragment == null) {
            fragment = TripFragment.newInstance(id,isAdmin);
        }
        return fragment;
    }

    private void setToolbar(){
        mToolbar = mActivityTripBinding.toolbar;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*
    empty fragment back stack and return to parent activity
     */


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() >= 1) {
            fragmentManager.popBackStack();
        }
        //get fragment by the tag in fragment manager
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
}
