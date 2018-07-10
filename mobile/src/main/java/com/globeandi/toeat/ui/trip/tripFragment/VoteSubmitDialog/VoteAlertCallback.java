package com.globeandi.toeat.ui.trip.tripFragment.VoteSubmitDialog;

public interface VoteAlertCallback {

    void dismissDialogOnCancel();

    void dismissDialogOnSubmit();

    void submitVote();

    void handleError(Throwable throwable);
}
