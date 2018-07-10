package com.globeandi.toeat.ui.section.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.databinding.FragmentAboutBinding;
import com.globeandi.toeat.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by jay on 3/21/2018.
 */

public class AboutFragment extends BaseFragment<FragmentAboutBinding,AboutViewModel> implements AboutNavigator {
    //find/ remove/ fragment by his class name
    public static final String TAG = AboutFragment.class.getSimpleName();

    @Inject
    AboutViewModel mAboutViewModel;

    public static AboutFragment newInstance(){
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public AboutViewModel getViewModel() {
        return mAboutViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAboutViewModel.setNavigator(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void notAvailable() {
        Toast.makeText(getBaseActivity(), "Not available in Beta version", Toast.LENGTH_SHORT).show();
    }
}
