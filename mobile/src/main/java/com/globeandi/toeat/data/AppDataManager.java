package com.globeandi.toeat.data;

import android.content.Context;

import com.globeandi.toeat.data.local.db.DbHelper;
import com.globeandi.toeat.data.local.prefs.PreferencesHelper;
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
import com.globeandi.toeat.data.models.db.Group;
import com.globeandi.toeat.data.models.db.Trip;
import com.globeandi.toeat.data.models.db.User;
import com.globeandi.toeat.data.remote.ApiHeader;
import com.globeandi.toeat.data.remote.ApiHelper;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jay on 3/23/2018.
 * for db wrapper methods of db helper
 * this methods will be called on the view models
 */
@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final ApiHelper mApiHelper;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, Gson gson, ApiHelper apiHelper, PreferencesHelper preferencesHelper) {

        this.mContext = context;
        this.mDbHelper = dbHelper;
        this.mGson = gson;
        this.mApiHelper = apiHelper;
        this.mPreferencesHelper = preferencesHelper;
    }

    @Override
    public boolean insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return null;
    }

    @Override
    public Observable<Boolean> insertGroup(Group group) {
        return null;
    }

    @Override
    public Observable<List<User>> getAllGroups() {
        return null;
    }

    @Override
    public Observable<Boolean> insertTrip(Trip trip) {
        return null;
    }

    @Override
    public Observable<List<Trip>> getAllTrips() {
        return null;
    }

    @Override
    public void setProfilePicturePath(String path) {
        mPreferencesHelper.setProfilePicturePath(path);
    }

    @Override
    public String getProfilePicturePath() {
        return mPreferencesHelper.getProfilePicturePath();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public String getRefreshToken() {
        return mPreferencesHelper.getRefreshToken();
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        mPreferencesHelper.setRefreshToken(refreshToken);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public void setCurrentUserName(String username) {
        mPreferencesHelper.setCurrentUserName(username);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public boolean isTripCreated() {
        return mPreferencesHelper.isTripCreated();
    }

    @Override
    public void setTripCrreated(boolean created) {
        mPreferencesHelper.setTripCrreated(created);
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<LoginResponse> doServerLoginRequest(LoginRequest.ServerLoginRequest loginRequest) {
        return mApiHelper.doServerLoginRequest(loginRequest);
    }

    @Override
    public Single<RegisterResponse> doServerRegisterRequest(RequestBody name, RequestBody email, RequestBody password, RequestBody imgDescription, MultipartBody.Part image) {
        return mApiHelper.doServerRegisterRequest(name, email, password, imgDescription, image);
    }

    @Override
    public Single<LogoutResponse> doLogoutRequest() {
        return mApiHelper.doLogoutRequest();
    }

    @Override
    public Single<GroupResponse> doCreateGroupCall(String name, String description) {
        return mApiHelper.doCreateGroupCall(name, description);
    }

    @Override
    public Single<GroupResponse> doUpdateGroupCall(Long groupId) {
        return mApiHelper.doUpdateGroupCall(groupId);
    }

    @Override
    public Single<GroupResponse> doDeleteGroupCall(Long groupId) {
        return mApiHelper.doDeleteGroupCall(groupId);
    }

    @Override
    public Single<GroupResponse> getUserGroupsCall() {
        return mApiHelper.getUserGroupsCall();
    }

    @Override
    public Single<GroupResponse> doJoinGroup(Long groupId) {
        return mApiHelper.doJoinGroup(groupId);
    }

    @Override
    public Single<TripResponse> doCreateTripCall(Long groupId, TripResponse.Trip trip) {
        return mApiHelper.doCreateTripCall(groupId, trip);
    }

    @Override
    public Single<TripResponse> getActiveTripCall(Long groupId) {
        return mApiHelper.getActiveTripCall(groupId);
    }

    @Override
    public Single<TripHistoryResponse> getTripHistoryCall() {
        return mApiHelper.getTripHistoryCall();
    }

    @Override
    public Single<UpcomingTripsResponse> getUpcomingTripsCall() {
        return mApiHelper.getUpcomingTripsCall();
    }

    @Override
    public Single<TripResponse> doJoinTripCall(Long groupId, Long tripId) {
        return mApiHelper.doJoinTripCall(groupId, tripId);
    }

    @Override
    public Single<TripResponse> doUnJoinTripCall(Long groupId, Long tripId) {
        return mApiHelper.doUnJoinTripCall(groupId, tripId);
    }

    @Override
    public Single<TripResponse> doCompleteTrip(Long tripId) {
        return mApiHelper.doCompleteTrip(tripId);
    }

    @Override
    public Single<PlacesApiResponse> getRestaurantNearbySearch(String location, String rankBy, String type) {
        return mApiHelper.getRestaurantNearbySearch(location, rankBy, type);
    }

    @Override
    public Single<PlacesApiResponse> getRestaurantSpecificSearch(String type, String location, String keyword, int radius) {
        return mApiHelper.getRestaurantSpecificSearch(type, location, keyword, radius);
    }

    @Override
    public Single<VoteResponse> doSubmitVoteCall(Long groupId, Long tripId, Long placeId) {
        return mApiHelper.doSubmitVoteCall(groupId, tripId, placeId);
    }

    @Override
    public Single<VoteResponse> getVoteResults(Long tripId) {
        return mApiHelper.getVoteResults(tripId);
    }

    @Override
    public Single<SearchResponse> doUserSearch(Long groupId,String searchText) {
        return mApiHelper.doUserSearch(groupId,searchText);
    }

    @Override
    public Observable<SearchResponse> doUsernameSearch(String searchText) {
        return mApiHelper.doUsernameSearch(searchText);
    }

    @Override
    public Single<InviteResponse> doSendInvite(Long groupId, Long userId) {
        return mApiHelper.doSendInvite(groupId, userId);
    }

    @Override
    public Single<InviteResponse> doDeleteInvite(Long groupId, Long userId) {
        return mApiHelper.doDeleteInvite(groupId, userId);
    }

    @Override
    public Single<InviteResponse> getGroupInvites() {
        return mApiHelper.getGroupInvites();
    }


    @Override
    public void setUserAsLoggedOut() {
        updateUserSection(
                null,
                null,
                LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                (long) 0,
                null
        );
    }

    @Override
    public void updateUserSection(String accessToken, String refreshToken, LoggedInMode loggedInMode, String username, String email, Long userId, String profilePathPic) {
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserEmail(email);
        setProfilePicturePath(profilePathPic);
        setCurrentUserId(userId);
        setCurrentUserName(username);
    }
}
