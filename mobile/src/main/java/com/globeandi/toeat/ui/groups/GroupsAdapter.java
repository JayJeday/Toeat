package com.globeandi.toeat.ui.groups;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.databinding.ItemGroupViewBinding;
import com.globeandi.toeat.ui.base.BaseViewHolder;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<GroupResponse.Group> mGroupList;

    private  GroupItemNavigator mGroupItemNavigator;

    public GroupsAdapter(List<GroupResponse.Group> groupList) {
        this.mGroupList = groupList;
    }

    public void setGroupItemNavigator(GroupItemNavigator groupItemNavigator) {
        mGroupItemNavigator = groupItemNavigator;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGroupViewBinding groupViewBinding = ItemGroupViewBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GroupViewHolder(groupViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    //work has a setter to fill the list
    public void addItems(List<GroupResponse.Group> groupList){
        mGroupList.addAll(groupList);
        notifyDataSetChanged();
    }

    public void clearItems(){
        mGroupList.clear();
    }


    public class GroupViewHolder extends BaseViewHolder implements  GroupItemNavigator {

        private ItemGroupViewBinding mGroupViewBinding;

        private GroupItemViewModel mGroupItemViewModel;

        public GroupViewHolder(ItemGroupViewBinding binding) {
            super(binding.getRoot());
            mGroupViewBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final GroupResponse.Group group = mGroupList.get(position);
            mGroupItemViewModel = new GroupItemViewModel(group, this);
            mGroupViewBinding.setViewModel(mGroupItemViewModel);
            mGroupViewBinding.executePendingBindings();
        }

//        @Override
//        public void onGroupClicked(GroupResponse.Group group) {
//            Toast.makeText(itemView.getContext(), "Group id: " + group.getId(), Toast.LENGTH_SHORT).show();
//
//            itemView.getContext().startActivity(TripActivity.newIntent(itemView.getContext()).putExtra(TripActivity.EXTRA_GROUP_ID,group.getId()));
//
//            //Todo::to implement later using single live event
//            //mGroupsViewModel.getOpenGroupDetailsEvent().setValue(group.getId());
//        }

        @Override
        public void openGroupDetails(Long id, Boolean admin) {
            mGroupItemNavigator.openGroupDetails(id, admin);
        }
    }
}
