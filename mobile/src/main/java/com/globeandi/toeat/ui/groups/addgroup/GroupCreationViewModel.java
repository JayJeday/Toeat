package com.globeandi.toeat.ui.groups.addgroup;

import android.support.constraint.Group;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.ui.base.BaseViewModel;
import com.globeandi.toeat.util.CommonUtils;
import com.globeandi.toeat.util.rx.SchedulerProvider;

import java.util.HashSet;
import java.util.Set;

public class GroupCreationViewModel extends BaseViewModel<GroupCreationNavigation> {

    private static final String TAG = "GroupCreationViewModel";

    public GroupCreationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public Set<String> loginFieldsValidation(String groupName, String groupDesc) {
        Set<String> failuresFields = new HashSet<>();
        if (TextUtils.isEmpty(groupName)) {
            failuresFields.add("name");
        }
        if (TextUtils.isEmpty(groupDesc)) {
            failuresFields.add("description");
        }
        return failuresFields;
    }

    public void createGroup(String groupName, String groupDesc,Group group) {
        //create group call
        group.setVisibility(View.GONE);
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().doCreateGroupCall(groupName, groupDesc)
                        .doOnSuccess(groupResponse -> Log.d(TAG, "meessage" + groupResponse))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(groupResponse -> {
                            //return to group fragment
                            getNavigator().alertChangesEvent();
                            getNavigator().goBack();
                        }, throwable -> {
                            setIsLoading(false);
                            getNavigator().handleError(throwable);
                            group.setVisibility(View.VISIBLE);
                        })
        );
    }


    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void onClickCreateGroup() {
        getNavigator().createGroup();
    }
}
