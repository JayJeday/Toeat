package com.globeandi.toeat.ui.pager.upcomingtrips;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.databinding.ItemUpcomingTripViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;

public class UpcomingTripAdapter extends ListAdapter<TripResponse.Trip, BaseViewHolder> {

    UpcomingTripAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemUpcomingTripViewBinding binding = ItemUpcomingTripViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UpcomingTripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public class UpcomingTripViewHolder extends BaseViewHolder{

        private final  ItemUpcomingTripViewBinding binding;

        private UpcomingTripFragmentItemViewModel mUpcomingTripFragmentItemViewModel;


        UpcomingTripViewHolder(ItemUpcomingTripViewBinding viewBinding){
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        @Override
        public void onBind(int position) {
            TripResponse.Trip trip = getItem(position);
            mUpcomingTripFragmentItemViewModel = new UpcomingTripFragmentItemViewModel(trip);
            binding.setViewModel(mUpcomingTripFragmentItemViewModel);
            binding.executePendingBindings();
        }
    }

    /*
    Todo:: refactor this with generics
     */
    public static final DiffUtil.ItemCallback<TripResponse.Trip> DIFF_CALLBACK = new DiffUtil.ItemCallback<TripResponse.Trip>() {
        @Override
        public boolean areItemsTheSame(@NonNull TripResponse.Trip oldItem, @NonNull TripResponse.Trip newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TripResponse.Trip oldItem, @NonNull TripResponse.Trip newItem) {
            //verify this
            return oldItem.getId().equals(newItem.getId())
                    && oldItem.getTitle().equals(newItem.getTitle());
        }
    };
}
