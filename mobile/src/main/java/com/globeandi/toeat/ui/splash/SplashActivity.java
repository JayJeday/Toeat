package com.globeandi.toeat.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.ActivitySplashBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.ui.login.LoginActivity;
import com.globeandi.toeat.ui.section.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding,SplashViewModel> implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;

    private ActivitySplashBinding mSplashBinding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.redirectToActivity();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void openLoginActivity() {
       Intent intent = LoginActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openSectionActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }
}
