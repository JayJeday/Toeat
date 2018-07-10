package com.globeandi.toeat.ui.section;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.ActivityMainBinding;
import com.globeandi.toeat.databinding.NavHeaderMainBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.ui.groups.GroupsFragment;
import com.globeandi.toeat.ui.login.LoginActivity;
import com.globeandi.toeat.ui.section.invite.InviteFragment;
import com.globeandi.toeat.ui.section.about.AboutFragment;
import com.globeandi.toeat.ui.section.settings.SettingFragment;
import com.globeandi.toeat.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by jay on 3/17/2018.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, SectionViewModel> implements SectionNavigator, HasSupportFragmentInjector {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    SectionViewModel mSectionViewModel;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ActivityMainBinding mActivityMainBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public SectionViewModel getViewModel() {
        return mSectionViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mSectionViewModel.setNavigator(this);
        //reset light bar status
        getWindow().getDecorView().setSystemUiVisibility(0);
        setToolbar();
        setUpNavDrawer();
        setupDefaultFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mDrawerLayout != null){
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        }

    }

    @Override
    public void onFragmentAttached() {

    }


    /*
    if fragment exist, detached from fragment manager
     */
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
    public void handleError(Throwable errors) {

    }

    /*
            when login out
     */
    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    /*
    setup toolbar
     */
    private void setToolbar() {
        mToolbar = mActivityMainBinding.toolbar;
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    /*
        Setting up navigation Drawer
     */
    private void setUpNavDrawer() {

        mDrawerLayout = mActivityMainBinding.drawerView;
        mNavigationView = mActivityMainBinding.navigationView;


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setupNavMenu();
        //set all the menu things
        mSectionViewModel.onNavMenuCreated();
    }

    /*
        setting up navigation for the navigation drawer
     */
    private void setupNavMenu() {
        //set data view model to nav header
        NavHeaderMainBinding headerMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(headerMainBinding.getRoot());
        headerMainBinding.setViewModel(mSectionViewModel);

        //setup nav items
        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.navItemGroups:
                            showSpecificFragment(GroupsFragment.newInstance(), GroupsFragment.TAG);
                            return true;
                        case R.id.navItemInvites:
                            showSpecificFragment(InviteFragment.newInstance(), InviteFragment.TAG);
                            return true;
                        case R.id.navItemAbout:
                            showSpecificFragment(AboutFragment.newInstance(), AboutFragment.TAG);
                            return true;
//                        case R.id.navItemSetting:
//                            showSpecificFragment(SettingFragment.newInstance(), SettingFragment.TAG);
//                            return true;
                        case R.id.navItemLogout:
                            mSectionViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showSpecificFragment(Fragment fragment, String tag) {
//        lockDrawer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().disallowAddToBackStack()
                .replace(R.id.sectionContainer, fragment, tag).commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void setupDefaultFragment() {
        GroupsFragment groupsFragment = (GroupsFragment) getSupportFragmentManager().findFragmentById(R.id.sectionContainer);
        if (groupsFragment == null) {
            groupsFragment = (GroupsFragment) GroupsFragment.newInstance();
        }
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), groupsFragment, R.id.sectionContainer);
    }


    private void lockDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void unlockDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
