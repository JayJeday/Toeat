package com.globeandi.toeat.ui.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.ActivityLoginBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.ui.registration.RegistrationActivity;
import com.globeandi.toeat.ui.section.MainActivity;
import com.globeandi.toeat.util.SnackBarFactory;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by jay on 3/17/2018.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    LoginViewModel mLoginViewModel;

    private ActivityLoginBinding mActivityLoginBinding;

    /**
     * Call this method to make login activity appear
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    public void login() {
        //one way data binding
        String email = mActivityLoginBinding.etEmail.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();

        ViewGroup transitionsContainer = mActivityLoginBinding.clLogin;
        //set a transitions of slide for the view that will appear
        TransitionManager.beginDelayedTransition(transitionsContainer,new Slide(Gravity.END));
        clearValidationTv();

        //manage visibility when loading
        Group loginGroup = mActivityLoginBinding.group;
        //validate fields
        Set<String> failureFields = mLoginViewModel.loginFieldsValidation(email, password);

        //view to display the snack bar
        View view = mActivityLoginBinding.getRoot();

        if (failureFields.isEmpty()) {

            //validate if there is a connection before making a call
            if (isNetworkConnected()) {
                mLoginViewModel.login(email, password, loginGroup);
            } else {
                //show toast with retry action
                View.OnClickListener listener = view1 -> login();

                SnackBarFactory.createNetworkRetrySnackBar(this, view, getString(R.string.network_snackbar_message), listener).show();
            }

        } else {
            for (String field : failureFields) {
                switch (field) {
                    case "email":
                        mActivityLoginBinding.tvEmailValidationL.setVisibility(View.VISIBLE);
                        break;
                    case "password":
                        mActivityLoginBinding.tvPasswordValidationL.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

    }

    private void clearValidationTv() {
        mActivityLoginBinding.tvLoginCredValidation.setVisibility(View.GONE);
        mActivityLoginBinding.tvEmailValidationL.setVisibility(View.GONE);
        mActivityLoginBinding.tvPasswordValidationL.setVisibility(View.GONE);
    }

    @Override
    public void openSectionActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        // destroy activity
        finish();
    }

    @Override
    public void openRegistrationActivity() {
        Intent intent = RegistrationActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void handleError(Throwable throwable) {
        //TODO:: validate if network is down orr if other error ocurred
        mActivityLoginBinding.tvLoginCredValidation.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBindingVariable() {
        //get the variables from layout data to set it manually
        return BR.viewModel;
    }

    @Override
    public LoginViewModel getViewModel() {
        //get the view model from dagger
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //return the data binding
        mActivityLoginBinding = getViewDataBinding();
        //this is necessary so we can call the interface methods from the view models
        mLoginViewModel.setNavigator(this);

//        Window w = getWindow(); // in Activity's onCreate() for instance
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }



    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}
