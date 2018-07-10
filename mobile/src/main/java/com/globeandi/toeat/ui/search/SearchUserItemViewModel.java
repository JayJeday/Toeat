package com.globeandi.toeat.ui.search;

import android.databinding.ObservableField;

import com.globeandi.toeat.data.models.api.SearchResponse;

public class SearchUserItemViewModel {

    public final ObservableField<String> userName;

    public final ObservableField<Boolean> isInvited;

    public final SearchResponse.User mUser;

    public final SearchUserItemNavigator mListener;

    public SearchUserItemViewModel(SearchResponse.User user,  SearchUserItemNavigator listener) {
        mUser = user;
        mListener = listener;
        this.userName = new ObservableField<>(mUser.getName());
        this.isInvited = new ObservableField<>(mUser.isInvited());
    }

    public void onSendUninviteButtonPressed(){
        mListener.onSendUninviteClicked(mUser,isInvited);
    }

    public void onSendInviteButtonPressed(){
        mListener.onSendInviteClicked(mUser,isInvited);
    }

    public void onViewProfileButtonPressed(){
        mListener.onViewProfileClicked(mUser);
    }
}
