package com.globeandi.toeat.ui.search;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.globeandi.toeat.data.models.api.SearchResponse;


import com.globeandi.toeat.databinding.ItemSearchUserViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;

public class SearchUserAdapter extends ListAdapter<SearchResponse.User, BaseViewHolder> {

    private SearchUserItemNavigator mUserItemNavigator;

     SearchUserAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setUserItemNavigator(SearchUserItemNavigator userItemNavigator) {
        mUserItemNavigator = userItemNavigator;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         ItemSearchUserViewBinding itemSearchUserViewBinding =  ItemSearchUserViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
         return new SearchUserViewHolder(itemSearchUserViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
         holder.onBind(position);
    }


    public class SearchUserViewHolder extends BaseViewHolder implements SearchUserItemNavigator {

        SearchUserItemViewModel mItemViewModel;

        ItemSearchUserViewBinding binding;

        public SearchUserViewHolder(ItemSearchUserViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        @Override
        public void onBind(int position) {
            final SearchResponse.User user = getItem(position);

            //set view model
            mItemViewModel = new SearchUserItemViewModel(user,this);
            binding.setViewModel(mItemViewModel);
            binding.executePendingBindings();
        }

        @Override
        public void onSendInviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited) {
            mUserItemNavigator.onSendInviteClicked(user, isInvited);
        }

        @Override
        public void onSendUninviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited) {
            mUserItemNavigator.onSendUninviteClicked(user, isInvited);
        }

        @Override
        public void onViewProfileClicked(SearchResponse.User user) {
            mUserItemNavigator.onViewProfileClicked(user);
        }

        @Override
        public void handleError(Throwable throwable) {

        }
    }


    public static final DiffUtil.ItemCallback<SearchResponse.User> DIFF_CALLBACK = new DiffUtil.ItemCallback<SearchResponse.User>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchResponse.User oldItem, @NonNull SearchResponse.User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchResponse.User oldItem, @NonNull SearchResponse.User newItem) {
            //verify this
            return oldItem.getId().equals(newItem.getId())
                    && oldItem.getName().equals(newItem.getName());
        }
    };
}
