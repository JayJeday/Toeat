package com.globeandi.toeat.ui.trip.tripFragment;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.databinding.ItemVotingListViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;

/*
        Adapter from the list
 */
public class TripFragmentVotingListAdapter extends ListAdapter<TripResponse.PlacesToEat, BaseViewHolder> {

    TripFragmentVotingListAdapter() {
        super(TripFragmentVotingListAdapter.DIFF_CALLBACK);
    }

    private TripFragmentVotingItemViewModel.VotingItemListener mVotingItemListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVotingListViewBinding viewBinding = ItemVotingListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VotingPlacesViewHolder(viewBinding);
    }

    public void setVotingItemListener(TripFragmentVotingItemViewModel.VotingItemListener votingItemListener) {
        mVotingItemListener = votingItemListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public class VotingPlacesViewHolder extends BaseViewHolder implements TripFragmentVotingItemViewModel.VotingItemListener {

        TripFragmentVotingItemViewModel mItemViewModel;

        ItemVotingListViewBinding binding;

        public VotingPlacesViewHolder(ItemVotingListViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        @Override
        public void onBind(int position) {
            final TripResponse.PlacesToEat place = getItem(position);

            //set view model
            mItemViewModel = new TripFragmentVotingItemViewModel(this,place);
            binding.setViewModel(mItemViewModel);
            binding.executePendingBindings();

        }

        @Override
        public void onVotingSelected(View view, TripResponse.PlacesToEat place) {
            //place values to global variable so we can passed to fragment
            mVotingItemListener.onVotingSelected(view, place);
        }
    }

    public static final DiffUtil.ItemCallback<TripResponse.PlacesToEat> DIFF_CALLBACK = new DiffUtil.ItemCallback<TripResponse.PlacesToEat>() {
        @Override
        public boolean areItemsTheSame(@NonNull TripResponse.PlacesToEat oldItem, @NonNull TripResponse.PlacesToEat newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TripResponse.PlacesToEat oldItem, @NonNull TripResponse.PlacesToEat newItem) {
            //verify this
            return oldItem.getId().equals(newItem.getId())
                    && oldItem.getName().equals(newItem.getName());
        }
    };
}
