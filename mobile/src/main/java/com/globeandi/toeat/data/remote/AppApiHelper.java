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

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@Singleton
public class AppApiHelper implements ApiHelper {

    private final ApiHeader mApiHeader;

    private final ToeatService mToeatService;

    private final GooglePlacesService mGooglePlacesService;


    @Inject
    public AppApiHelper(ApiHeader apiHeader, ToeatService toeatService, GooglePlacesService googlePlacesService) {
        this.mApiHeader = apiHeader;
        this.mToeatService = toeatService;
        this.mGooglePlacesService = googlePlacesService;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doServerLoginRequest(LoginRequest.ServerLoginRequest loginRequest) {
        //    return mBasicRetrofit.create(ToeatService.class).login(loginRequest);
        return mToeatService.login(loginRequest);
    }

    @Override
    public Single<RegisterResponse> doServerRegisterRequest(RequestBody name, RequestBody email, RequestBody password, RequestBody imgDescription, MultipartBody.Part image) {
        //   return mBasicRetrofit.create(ToeatService.class).register(name, email, password, imgDescription, image);
        return mToeatService.register(name, email, password, imgDescription, image);
    }

    @Override
    public Single<LogoutResponse> doLogoutRequest() {
        return mToeatService.logout();
    }

    @Override
    public Single<GroupResponse> doCreateGroupCall(String name, String description) {
        return mToeatService.createGroup(name, description);
    }

    @Override
    public Single<GroupResponse> doUpdateGroupCall(Long groupId) {
        return mToeatService.updateGroup(groupId);
    }

    @Override
    public Single<GroupResponse> doDeleteGroupCall(Long groupId) {
        return mToeatService.deleteGroup(groupId);
    }

    @Override
    public Single<GroupResponse> getUserGroupsCall() {
        return mToeatService.getUserGroups();
    }

    @Override
    public Single<GroupResponse> doJoinGroup(Long groupId) {
        return mToeatService.joinGroup(groupId);
    }

    @Override
    public Single<TripResponse> doCreateTripCall(Long groupId, TripResponse.Trip trip) {
        return mToeatService.createTrip(groupId, trip);
    }

    @Override
    public Single<TripResponse> getActiveTripCall(Long groupId) {
        return mToeatService.getActiveTrip(groupId);
    }

    @Override
    public Single<TripHistoryResponse> getTripHistoryCall() {
        return mToeatService.getHistoryTrips();
    }

    @Override
    public Single<UpcomingTripsResponse> getUpcomingTripsCall() {
        return mToeatService.getUpcomingTrips();
    }

    @Override
    public Single<TripResponse> doJoinTripCall(Long groupId, Long tripId) {
        return mToeatService.joinTrip(groupId, tripId);
    }

    @Override
    public Single<TripResponse> doUnJoinTripCall(Long groupId, Long tripId) {
        return mToeatService.unJoinTrip(groupId, tripId);
    }

    @Override
    public Single<TripResponse> doCompleteTrip(Long tripId) {
        return mToeatService.completeTrip(tripId);
    }

    @Override
    public Single<PlacesApiResponse> getRestaurantNearbySearch(String location, String rankBy, String type) {
        return mGooglePlacesService.getNearbyRestaurant(location, rankBy, type);
    }

    @Override
    public Single<PlacesApiResponse> getRestaurantSpecificSearch(String type, String location, String keyword, int radius) {
        return mGooglePlacesService.getSpecificRestaurantByName(type, location, keyword, radius);
    }

    @Override
    public Single<VoteResponse> doSubmitVoteCall(Long groupId, Long tripId, Long placeId) {
        return mToeatService.updatePlaceVote(groupId, tripId, placeId);
    }

    @Override
    public Single<VoteResponse> getVoteResults(Long tripId) {
        return mToeatService.getResults(tripId);
    }

    @Override
    public Single<SearchResponse> doUserSearch(Long groupId,String searchText) {
        return mToeatService.searchUser(groupId,searchText);
    }

    @Override
    public Observable<SearchResponse> doUsernameSearch(String searchText) {
        return mToeatService.searchUserByUsername(searchText);
    }

    @Override
    public Single<InviteResponse> doSendInvite(Long groupId, Long userId) {
        return mToeatService.inviteUser(groupId, userId);
    }

    @Override
    public Single<InviteResponse> doDeleteInvite(Long groupId, Long userId) {
        return mToeatService.deleteInvite(groupId,userId);
    }

    @Override
    public Single<InviteResponse> getGroupInvites() {
        return mToeatService.getGroupInvites();
    }
}
