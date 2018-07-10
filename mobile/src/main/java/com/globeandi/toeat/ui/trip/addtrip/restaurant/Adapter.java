package com.globeandi.toeat.ui.trip.addtrip.restaurant;


import android.os.Build;
import android.support.annotation.NonNull;

import android.support.annotation.RequiresApi;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.selection.SelectionTracker;


import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.databinding.ItemPlaceViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;
import com.globeandi.toeat.util.rcSelection.PlaceDetail;

public class Adapter extends ListAdapter<PlacesApiResponse.Place, BaseViewHolder> {

    private SelectionTracker<Long> selectionTracker;

    Adapter() {
        super(Adapter.DIFF_CALLBACK);
    }

    public void setSelectionTracker(
            SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }


    static class Predicate extends SelectionTracker.SelectionPredicate<Long> {

        @Override
        public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSetStateAtPosition(int position, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSelectMultiple() {
            return true;
        }
    }

    public class PlacesViewHolder extends BaseViewHolder {

        private ItemPlaceViewBinding binding;

        private PlaceDetail details;

        private RestaurantPlacesItemViewModel mRestaurantPlacesItemViewModel;

        PlacesViewHolder(@NonNull ItemPlaceViewBinding itemRecyclerBinding) {
            super(itemRecyclerBinding.getRoot());
            binding = itemRecyclerBinding;
            details = new PlaceDetail();
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBind(int position) {
            details.setPosition(position);
            final PlacesApiResponse.Place place = getItem(position);

            //bind item to the view holder Item
            mRestaurantPlacesItemViewModel = new RestaurantPlacesItemViewModel(place);
            binding.setViewModel(mRestaurantPlacesItemViewModel);
            binding.executePendingBindings();

            //manage Item selection
            if (selectionTracker != null) {

                if (Adapter.this.selectionTracker.isSelected(details.getSelectionKey())) {
                    binding.getViewModel().itemBackground.set(binding.getRoot().getContext().getColor(R.color.colorAccent));
                    binding.getRoot().setActivated(true);
                }else{
                    binding.getRoot().setActivated(false);
                    binding.getViewModel().itemBackground.set(binding.getRoot().getContext().getColor(R.color.white));
                }
            }

        }

        public PlaceDetail getPlaceDetails() {
            return details;
        }


    }
// else{
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return mPlaces.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return places.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//
//                    PlacesApiResponse.Place old = mPlaces.get(oldItemPosition);
//                    PlacesApiResponse.Place place = places.get(newItemPosition);
//                    return old.getId().equals(place.getId());
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    PlacesApiResponse.Place old = mPlaces.get(oldItemPosition);
//                    PlacesApiResponse.Place place = places.get(newItemPosition);
//
//                    return old.getId().equals(place.getId())
//                            && old.getName().equals(place.getName())
//                            && old.getGeometry() == place.getGeometry();
//                }
//            });
//            mPlaces = places;
//            diffResult.dispatchUpdatesTo(this);
//        }
    //   }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPlaceViewBinding itemPlaceViewBinding = ItemPlaceViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PlacesViewHolder(itemPlaceViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public static final DiffUtil.ItemCallback<PlacesApiResponse.Place> DIFF_CALLBACK = new DiffUtil.ItemCallback<PlacesApiResponse.Place>() {
        @Override
        public boolean areItemsTheSame(@NonNull PlacesApiResponse.Place oldItem, @NonNull PlacesApiResponse.Place newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlacesApiResponse.Place oldItem, @NonNull PlacesApiResponse.Place newItem) {
            //verify this
            return oldItem.getId().equals(newItem.getId())
                    && oldItem.getName().equals(newItem.getName());
        }
    };

}
