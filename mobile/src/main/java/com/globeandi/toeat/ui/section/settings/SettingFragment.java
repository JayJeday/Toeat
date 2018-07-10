package com.globeandi.toeat.ui.section.settings;

import android.os.Bundle;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.FragmentSettingsBinding;
import com.globeandi.toeat.ui.base.BaseFragment;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.ui.section.about.AboutFragment;

import javax.inject.Inject;

/**
 * Created by jay on 3/21/2018.
 */

public class SettingFragment extends BaseFragment<FragmentSettingsBinding,SettingViewModel> {
    //find/ remove/ fragment by his class name
    public static final String TAG = SettingFragment.class.getSimpleName();

    @Inject
    SettingViewModel mSettingViewModel;

    public static SettingFragment newInstance(){
        Bundle args = new Bundle();
        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setArguments(args);
        return settingFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public SettingViewModel getViewModel() {
        return mSettingViewModel;
    }
}
