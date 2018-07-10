package com.globeandi.toeat.ui.groups.addgroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;

import com.globeandi.toeat.databinding.ActivityGroupCreateBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.util.AppConstants;
import com.globeandi.toeat.util.MessageEvents;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import javax.inject.Inject;


public class GroupCreationActivity extends BaseActivity<ActivityGroupCreateBinding, GroupCreationViewModel> implements GroupCreationNavigation {

    public static final String TAG = GroupCreationActivity.class.getSimpleName();

    @Inject
    GroupCreationViewModel mGroupCreationViewModel;

    private ActivityGroupCreateBinding mActivityGroupCreateBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, GroupCreationActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_create;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public GroupCreationViewModel getViewModel() {
        return mGroupCreationViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGroupCreationViewModel.setNavigator(this);

        mActivityGroupCreateBinding = getViewDataBinding();

        setToolbar();

        //Todo:: find better solution in phase 3 material design
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(0);
        textWatcherListener();
    }

    @SuppressLint("CheckResult")
    private void textWatcherListener() {
        //text watcher for group name and desc
        EditText groupNameEt = mActivityGroupCreateBinding.etGroupName;
        RxTextView.textChanges(groupNameEt).subscribe(text -> {
            mActivityGroupCreateBinding.tvGroupNameCounter.setText(String.valueOf(text.length()));

            if (text.length() == 20) {
                mActivityGroupCreateBinding.lGroupNameCounter.setBackground(ContextCompat.getDrawable(this, R.drawable.counter_limit_bg));
            } else {
                mActivityGroupCreateBinding.lGroupNameCounter.setBackground(ContextCompat.getDrawable(this, R.drawable.counter_normal_bg));
            }
        });

        EditText groupDescEt = mActivityGroupCreateBinding.etGroupDesc;
        RxTextView.textChanges(groupDescEt).subscribe(text -> {
            mActivityGroupCreateBinding.tvGroupDescCounter.setText(String.valueOf(text.length()));

            if (text.length() == 25) {
                mActivityGroupCreateBinding.lGroupDescCounter.setBackground(ContextCompat.getDrawable(this, R.drawable.counter_limit_bg));
            } else {
                mActivityGroupCreateBinding.lGroupDescCounter.setBackground(ContextCompat.getDrawable(this, R.drawable.counter_normal_bg));
            }
        });
    }


    private void setToolbar() {
        Toolbar toolbar = mActivityGroupCreateBinding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void createGroup() {
        String groupName = mActivityGroupCreateBinding.etGroupName.getText().toString();
        String groupDesc = mActivityGroupCreateBinding.etGroupDesc.getText().toString();

        ViewGroup transitionsContainer = mActivityGroupCreateBinding.clGroupCreate;
        TransitionManager.beginDelayedTransition(transitionsContainer, new Slide(Gravity.END));

        clearValidationTv();

        Group groupUis = mActivityGroupCreateBinding.group2;

        Set<String> failuresField = mGroupCreationViewModel.loginFieldsValidation(groupName, groupDesc);
        if (failuresField.isEmpty()) {
            mGroupCreationViewModel.createGroup(groupName, groupDesc,groupUis);
        } else {
            for (String field : failuresField) {
                switch (field) {
                    case "name":
                        mActivityGroupCreateBinding.tvGroupNameValidation.setVisibility(View.VISIBLE);
                        break;
                    case "description":
                        mActivityGroupCreateBinding.tvGroupDescValidation.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    public void alertChangesEvent() {
        MessageEvents events = new MessageEvents();
        events.setChangeAlert(true);
        EventBus.getDefault().post(events);
    }

    private void clearValidationTv() {
        mActivityGroupCreateBinding.tvGroupNameValidation.setVisibility(View.GONE);
        mActivityGroupCreateBinding.tvGroupDescValidation.setVisibility(View.GONE);
    }

    @Override
    public void goBack() {
        finish();
    }
}
