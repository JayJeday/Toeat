package com.globeandi.toeat.data.remote;


import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.LoginRequest;
import com.globeandi.toeat.data.models.api.LoginResponse;
import com.globeandi.toeat.data.models.api.LogoutResponse;
import com.globeandi.toeat.data.models.api.PlacesApiResponse;
import com.globeandi.toeat.data.models.api.RegisterResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.data.models.api.TripHistoryResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.data.models.api.UpcomingTripsResponse;
import com.globeandi.toeat.data.models.api.VoteResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doServerLoginRequest(LoginRequest.ServerLoginRequest loginRequest);

    /*
       for the Service registration
    */
    Single<RegisterResponse> doServerRegisterRequest(RequestBody name, RequestBody email,
                                                     RequestBody password, RequestBody imgDescription,
                                                     MultipartBody.Part image);

    Single<LogoutResponse> doLogoutRequest();

    //**** Group calls ********
    Single<GroupResponse> doCreateGroupCall(String name, String description);

    Single<GroupResponse> doUpdateGroupCall(Long groupId);

    Single<GroupResponse> doDeleteGroupCall(Long groupId);

    Single<GroupResponse> getUserGroupsCall();

    Single<GroupResponse> doJoinGroup(Long groupId);

    //**** trip calls
    Single<TripResponse> doCreateTripCall(Long groupId, TripResponse.Trip trip);

    Single<TripResponse> getActiveTripCall(Long groupId);

    Single<TripHistoryResponse> getTripHistoryCall();

    Single<UpcomingTripsResponse> getUpcomingTripsCall();

    //join trip
    Single<TripResponse> doJoinTripCall(Long groupId, Long tripId);

    //leave trip
    Single<TripResponse> doUnJoinTripCall(Long groupId, Long tripId);

    Single<TripResponse> doCompleteTrip(Long tripId);

    //Place api call
    Single<PlacesApiResponse> getRestaurantNearbySearch(String location, String rankBy, String type);

    Single<PlacesApiResponse> getRestaurantSpecificSearch(String type, String location, String name, int radius);

    //Vote api call
    Single<VoteResponse> doSubmitVoteCall(Long groupId, Long tripId, Long placeId);

    Single<VoteResponse> getVoteResults(Long tripId);

    //Search user
    Single<SearchResponse> doUserSearch(Long groupId,String searchText);
    //filter search
    Observable<SearchResponse> doUsernameSearch(String searchText);

    //Invite Management
    Single<InviteResponse> doSendInvite(Long groupId,Long userId);

    Single<InviteResponse> doDeleteInvite(Long groupId,Long userId);

    Single<InviteResponse> getGroupInvites();

}
