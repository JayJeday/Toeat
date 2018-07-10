package com.globeandi.toeat.ui.section.invite;


import android.databinding.ObservableField;
import com.globeandi.toeat.data.models.api.InviteResponse;


public class InviteItemViewModel {
    //Observables ->  keep your data synced with the UI
    public final ObservableField<String> groupName;

    public final ObservableField<Long> groupId;

    public final InviteResponse.Invite mInvite;

    public final InviteItemViewModelListener mListener;

    public InviteItemViewModel(InviteResponse.Invite invite, InviteItemViewModelListener listener) {
        this.mInvite = invite;
        this.mListener = listener;
       this.groupName = new ObservableField<>(invite.getGroupName());
       this.groupId = new ObservableField<>(invite.getGroupId());
    }

    public void onAcceptInvitePressed(){
        mListener.acceptInvite(mInvite.getGroupId());
    }

    public void onCancelInvitePressed(){
        mListener.rejectInvite(mInvite.getGroupId());
    }
}
