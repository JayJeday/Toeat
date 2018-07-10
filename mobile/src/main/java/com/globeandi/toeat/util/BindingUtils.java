package com.globeandi.toeat.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.ui.groups.GroupsAdapter;
import com.globeandi.toeat.ui.pager.triphistory.TripHistoryAdapter;
import com.globeandi.toeat.ui.pager.upcomingtrips.UpcomingTripAdapter;
import com.globeandi.toeat.ui.search.SearchUserAdapter;
import com.globeandi.toeat.ui.section.invite.InviteAdapter;
import com.globeandi.toeat.ui.trip.addtrip.SelectedRestaurantAdapter;
import com.globeandi.toeat.ui.trip.addtrip.restaurant.Adapter;
import com.globeandi.toeat.ui.trip.tripFragment.TripFragmentVotingListAdapter;


import java.util.List;

public final class BindingUtils {

    private BindingUtils() {
        //class cant be instantiated
    }

    //Data binding the adapter to the item action
    /*
    list group represent the value from the xml
    action: adding it to the adapter/ where adapter updates occur
     */
    @BindingAdapter({"adapter"})
    public static void addGroupItems(RecyclerView recyclerView, List<GroupResponse.Group> groups) {
        GroupsAdapter adapter = (GroupsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(groups);
        }

    }

    @BindingAdapter({"adapter"})
    public static void addPlacesItems(RecyclerView recyclerView, List<PlacesApiResponse.Place> places) {
        Adapter adapter = (Adapter) recyclerView.getAdapter();
        if (adapter != null) {
            //fill the adapter
            adapter.submitList(places);
        }
    }

    @BindingAdapter({"app:data"})
    public static void addSelectedPlacesItems(RecyclerView recyclerView, List<PlacesApiResponse.Place> places) {
        SelectedRestaurantAdapter adapter = (SelectedRestaurantAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(places);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addInvitesItems(RecyclerView recyclerView, List<InviteResponse.Invite> invites) {
        InviteAdapter adapter = (InviteAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(invites);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addPlacesToEat(RecyclerView recyclerView,List<TripResponse.PlacesToEat>places){
        TripFragmentVotingListAdapter adapter = (TripFragmentVotingListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(places);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addTripsItems(RecyclerView recyclerView, List<TripResponse.Trip> trips) {

        if (recyclerView.getId() == R.id.rvHistoryTrips) {
            TripHistoryAdapter adapter = (TripHistoryAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.submitList(trips);
            }

        } else {
            UpcomingTripAdapter adapter = (UpcomingTripAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.submitList(trips);
            }
        }
    }
    /*
    Binding for date picker when  date changed
     */
    @BindingAdapter({"android:year", "android:month", "android:day", "android:onDateChanged"})
    public static void setDate(DatePicker view, int year, int month, int day, DatePicker.OnDateChangedListener listener) {
        view.init(year, month, day, listener);
    }

    /*
    Binding for icon place
     */
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).override(45, 45).into(imageView);
    }
}
