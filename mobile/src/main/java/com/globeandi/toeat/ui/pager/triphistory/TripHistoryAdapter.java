package com.globeandi.toeat.ui.pager.triphistory;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.databinding.ItemHistoryTripsBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripAdapter;


public class TripHistoryAdapter extends ListAdapter<TripResponse.Trip, BaseViewHolder> {

    public TripHistoryAdapter() {
        super(UpcomingTripAdapter.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemHistoryTripsBinding itemHistoryTripsBinding = ItemHistoryTripsBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new TripHistoryViewHolder(itemHistoryTripsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public class TripHistoryViewHolder extends BaseViewHolder {

        private final ItemHistoryTripsBinding mBinding;

        private TripHistoryItemViewModel mTripHistoryItemViewModel;

        public TripHistoryViewHolder(ItemHistoryTripsBinding itemHistoryTripsBinding) {
            super(itemHistoryTripsBinding.getRoot());
            this.mBinding = itemHistoryTripsBinding;
        }

        @Override
        public void onBind(int position) {
            final TripResponse.Trip trip = getItem(position);
            mTripHistoryItemViewModel = new TripHistoryItemViewModel(trip);
            mBinding.setViewModel(mTripHistoryItemViewModel);
            mBinding.executePendingBindings();
        }
    }

}
