package com.globeandi.toeat.ui.section.invite;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.databinding.FragmentInviteBinding;
import com.globeandi.toeat.ui.base.BaseFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jay on 3/21/2018.
 */

public class InviteFragment extends BaseFragment<FragmentInviteBinding, InviteViewModel> implements InviteNavigator, InviteItemViewModelListener{
    //find/ remove/ fragment by his class name
    public static final String TAG = InviteFragment.class.getSimpleName();

    @Inject
    InviteAdapter mInviteAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    FragmentInviteBinding mFragmentInviteBinding;
    private InviteViewModel mInviteViewModel;

    public static InviteFragment newInstance() {
        Bundle args = new Bundle();
        InviteFragment inviteFragment = new InviteFragment();
        inviteFragment.setArguments(args);
        return inviteFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public InviteViewModel getViewModel() {
        mInviteViewModel = ViewModelProviders.of(this, mViewModelFactory).get(InviteViewModel.class);
        return mInviteViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInviteViewModel.setNavigator(this);
        mInviteAdapter.setListener(this);
        getBaseActivity().getSupportActionBar().setTitle("Invites");
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentInviteBinding = getViewDataBinding();
        setUpRecycleView();

        subscribeToLiveData();
    }

    private void subscribeToLiveData(){
        mInviteViewModel.getInviteListLiveData().observe(this,invites -> mInviteViewModel.mInviteObservableList.addAll(invites));
    }

    private void setUpRecycleView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentInviteBinding.inviteRecycleView.setLayoutManager(mLayoutManager);
        mFragmentInviteBinding.inviteRecycleView.setItemAnimator(new DefaultItemAnimator());
        mFragmentInviteBinding.inviteRecycleView.setAdapter(mInviteAdapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }


    @Override
    public void acceptInvite(Long groupId) {
        mInviteViewModel.joinGroup(groupId);
        //fix for now need to notify the list has changed
        List<InviteResponse.Invite> list = Collections.emptyList();
        mInviteAdapter.submitList(list);
        mInviteViewModel.fetchInvites();
    }

    @Override
    public void rejectInvite(Long groupId) {
        mInviteViewModel.notJoinGroup(groupId);
        List<InviteResponse.Invite> list = Collections.emptyList();
        mInviteAdapter.submitList(list);
        mInviteViewModel.fetchInvites();
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
