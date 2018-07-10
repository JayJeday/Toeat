package com.globeandi.toeat.ui.groups;


import android.databinding.ObservableField;


import com.globeandi.toeat.SingleLiveEvent;
import com.globeandi.toeat.data.models.api.GroupResponse;


public class GroupItemViewModel  {

    public final ObservableField<Long> groupId;

    public final ObservableField<String> name;

    public final ObservableField<String> description;

    public final GroupItemNavigator mListener;

    public final GroupResponse.Group mGroup;

    public GroupItemViewModel(GroupResponse.Group group, GroupItemNavigator listener) {
        this.mGroup = group;
        this.mListener = listener;
        this.groupId = new ObservableField<>(mGroup.getGroupId());
        name = new ObservableField<>(mGroup.getName());
        description = new ObservableField<>(mGroup.getDescription());
    }

    /*
    method called on the layout onclick
     */
    public void onItemClick() {
        //get current group id/ passed to the parameter
        mListener.openGroupDetails(mGroup.getGroupId(),mGroup.getAdmin());
    }

//    public interface GroupItemViewModelListener {
//
//        //click specific group
//        void onItemClick(Group group);
//    }

}
