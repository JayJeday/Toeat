package com.globeandi.toeat.ui.section.invite;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.databinding.ItemInviteViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;


import java.util.List;

public class InviteAdapter extends ListAdapter<InviteResponse.Invite,BaseViewHolder> {

    private InviteItemViewModelListener mListener;

    public InviteAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setListener(InviteItemViewModelListener listener) {
        this.mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInviteViewBinding inviteViewBinding = ItemInviteViewBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InviteViewHolder(inviteViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        //    Bind item view model with the current item in the list
        holder.onBind(position);
    }


    public class InviteViewHolder extends BaseViewHolder implements InviteItemViewModelListener {

        private final ItemInviteViewBinding mBinding;

        private InviteItemViewModel inviteItemViewModel;

        public InviteViewHolder(ItemInviteViewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final InviteResponse.Invite invite = getItem(position);

            inviteItemViewModel = new InviteItemViewModel(invite,this);
            mBinding.setViewModel(inviteItemViewModel);
            mBinding.executePendingBindings();
        }


        @Override
        public void acceptInvite(Long groupId) {
            mListener.acceptInvite(groupId);
        }

        @Override
        public void rejectInvite(Long groupId) {
            mListener.rejectInvite(groupId);
        }
    }

    public static final DiffUtil.ItemCallback<InviteResponse.Invite> DIFF_CALLBACK = new DiffUtil.ItemCallback<InviteResponse.Invite>() {
        @Override
        public boolean areItemsTheSame(@NonNull InviteResponse.Invite oldItem, @NonNull InviteResponse.Invite newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull InviteResponse.Invite oldItem, @NonNull InviteResponse.Invite newItem) {
            //verify this
            return oldItem.getId().equals(newItem.getId())
                    && oldItem.getGroupName().equals(newItem.getGroupName());
        }
    };
}

