package com.globeandi.toeat.ui.groups;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.globeandi.toeat.SingleLiveEvent;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by jay on 3/21/2018.
 */

public class GroupsViewModel extends BaseViewModel<GroupsNavigator> {

    //list represent the items in the recycle views
    public final ObservableList<GroupResponse.Group> mGroupObservableList = new ObservableArrayList<>();
    //mutable list observer will be notify when list change on server
    private final MutableLiveData<List<GroupResponse.Group>> groupListLiveData;

    //subscriber for opening  group detail event
    private final SingleLiveEvent<Long> mOpenGroupDetailsEvent = new SingleLiveEvent<>();

    //subscriber for opening  group creation screen event
    private final SingleLiveEvent<Void> mNewGroupEvent = new SingleLiveEvent<>();

    public final ObservableBoolean groupsAddViewVisible = new ObservableBoolean();

    //notify when group list is empty
    public final ObservableBoolean empty = new ObservableBoolean(false);

    public GroupsViewModel(DataManager dataManager, SchedulerProvider scheduler) {
        super(dataManager, scheduler);
        groupListLiveData = new MutableLiveData<>();
        fetchGroups();
    }

    public void addGroupItemsToList(List<GroupResponse.Group> groups){
        mGroupObservableList.clear();
        mGroupObservableList.addAll(groups);
    }

    SingleLiveEvent<Long> getOpenGroupDetailsEvent() {
        return mOpenGroupDetailsEvent;
    }

    SingleLiveEvent<Void> getNewGroupEvent() {
        return mNewGroupEvent;
    }

    public void fetchGroups() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getUserGroupsCall()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(groupResponse -> {
                            if (groupResponse != null && groupResponse.getData() != null){
                                groupListLiveData.setValue(groupResponse.getData());
                                //if the list is empty show specific view
                                if(groupListLiveData.getValue().isEmpty()){
                                    empty.set(true);
                                }else {
                                    empty.set(false);
                                }
                            }
                                    setIsLoading(false);
                                },
                                throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleError(throwable);
                                }));
    }

    /*
        get the list from the server
     */
    public MutableLiveData<List<GroupResponse.Group>> getGroupListLiveData() {
        return groupListLiveData;
    }

    /**
     * FAB click listener
     */
    public void onOpenGroupCreationClick() {
        mNewGroupEvent.call();
    }



}
