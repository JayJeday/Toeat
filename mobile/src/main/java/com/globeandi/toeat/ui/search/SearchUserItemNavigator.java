package com.globeandi.toeat.ui.search;

import android.databinding.ObservableField;

import com.globeandi.toeat.data.models.api.SearchResponse;

public interface SearchUserItemNavigator {

    void onSendInviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited);

    void onSendUninviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited);

    void onViewProfileClicked(SearchResponse.User user);

    void handleError(Throwable throwable);
}
