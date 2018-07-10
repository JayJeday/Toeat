package com.globeandi.toeat.ui.groups.addgroup;

public interface GroupCreationNavigation {

    void handleError(Throwable throwable);

    void createGroup();

    void alertChangesEvent();

    void goBack();
}
