package com.globeandi.toeat.ui.groups;


import com.globeandi.toeat.data.models.api.GroupResponse;

public interface GroupItemUserActionsListener {

    void onGroupClicked(GroupResponse.Group group);
}
