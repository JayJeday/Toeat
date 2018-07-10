package com.globeandi.toeat.ui.trip.addtrip;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.databinding.ItemSelectedPlaceViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;
import com.globeandi.toeat.ui.trip.TripCreationItemViewModel;
import com.globeandi.toeat.ui.trip.addtrip.restaurant.Adapter;


public class SelectedRestaurantAdapter extends ListAdapter<PlacesApiResponse.Place, BaseViewHolder> {

    private SelectedRestaurantItemAction mSelectedRestaurantItemAction;
    private int defaultPosition = 0;


    public SelectedRestaurantAdapter() {
        super(Adapter.DIFF_CALLBACK);
    }

    public void setSelectedRestaurantItemAction(SelectedRestaurantItemAction selectedRestaurantItemAction) {
        mSelectedRestaurantItemAction = selectedRestaurantItemAction;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectedPlaceViewBinding itemSelectedPlaceViewBinding = ItemSelectedPlaceViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SelectedPlacesViewHolder(itemSelectedPlaceViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public class SelectedPlacesViewHolder extends BaseViewHolder implements SelectedRestaurantItemAction {

        ItemSelectedPlaceViewBinding itemSelectedPlaceViewBinding;
        TripCreationItemViewModel tripCreationItemViewModel;

        public SelectedPlacesViewHolder(ItemSelectedPlaceViewBinding binding) {
            super(binding.getRoot());
            this.itemSelectedPlaceViewBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final PlacesApiResponse.Place place = getItem(position);
            if (defaultPosition == position) {
                //if item is the first one. Select that
                mSelectedRestaurantItemAction.onPlaceSelected(place);
             //   itemSelectedPlaceViewBinding.cvBg.setSelected(true);
            }

            tripCreationItemViewModel = new TripCreationItemViewModel(place, this);

            itemSelectedPlaceViewBinding.setViewModel(tripCreationItemViewModel);
            itemSelectedPlaceViewBinding.executePendingBindings();


        }

        @Override
        public void onPlaceSelected(PlacesApiResponse.Place place) {
            //fix for now
            mSelectedRestaurantItemAction.onPlaceSelected(place);

        }
    }
}
