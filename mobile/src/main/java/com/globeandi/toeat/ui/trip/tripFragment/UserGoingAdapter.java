package com.globeandi.toeat.ui.trip.tripFragment;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.databinding.ItemUsergoingViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;
import com.globeandi.toeat.ui.search.SearchUserAdapter;

public class UserGoingAdapter extends ListAdapter<SearchResponse.User, BaseViewHolder> {

    public UserGoingAdapter() {
        super(SearchUserAdapter.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUsergoingViewBinding usergoingViewBinding = ItemUsergoingViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserGoingViewHolder(usergoingViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public class UserGoingViewHolder extends BaseViewHolder {

        TripFragmentUserGoingItemViewModel mViewModel;

        ItemUsergoingViewBinding binding;

        public UserGoingViewHolder(ItemUsergoingViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        @Override
        public void onBind(int position) {
            final SearchResponse.User user = getItem(position);

            //set view model
            mViewModel = new TripFragmentUserGoingItemViewModel(user);
            binding.setViewModel(mViewModel);
            binding.executePendingBindings();
        }
    }
}
