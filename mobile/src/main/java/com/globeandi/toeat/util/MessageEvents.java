package com.globeandi.toeat.util;

import com.globeandi.toeat.data.models.api.PlacesApiResponse;

import java.util.List;

/*
Class that handle changes in event bus events
 */
public class MessageEvents {

    private boolean changeAlert;

    private boolean isVoteCanceled;

    private boolean isVoteSubmited;

    private boolean tripCreated;


    private String dialogDateResult;

    private List<PlacesApiResponse.Place> places;

    private String dialogTimeResult;

    public boolean isTripCreated() {
        return tripCreated;
    }

    public void setTripCreated(boolean tripCreated) {
        this.tripCreated = tripCreated;
    }

    public void setPlaces(List<PlacesApiResponse.Place> places) {
        this.places = places;
    }

    public List<PlacesApiResponse.Place> getPlaces() {
        return places;
    }

    public String getDialogTimeResult() {
        return dialogTimeResult;
    }

    public void setDialogTimeResult(String dialogTimeResult) {
        this.dialogTimeResult = dialogTimeResult;
    }

    public boolean isVoteSubmited() {
        return isVoteSubmited;
    }

    public void setVoteSubmited(boolean voteSubmited) {
        isVoteSubmited = voteSubmited;
    }

    public boolean isVoteCanceled() {
        return isVoteCanceled;
    }

    public void setVoteCanceled(boolean voteCanceled) {
        isVoteCanceled = voteCanceled;
    }

    public boolean isChangeAlert() {
        return changeAlert;
    }

    public void setChangeAlert(boolean changeAlert) {
        this.changeAlert = changeAlert;
    }

    public String getDialogDateResult() {
        return dialogDateResult;
    }

    public void setDialogDateResult(String dialogDateResult) {
        this.dialogDateResult = dialogDateResult;
    }
}
