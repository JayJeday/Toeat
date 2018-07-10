package com.globeandi.toeat.data.remote;


import com.globeandi.toeat.data.models.api.GroupResponse;
import com.globeandi.toeat.data.models.api.InviteResponse;
import com.globeandi.toeat.data.models.api.LoginRequest;
import com.globeandi.toeat.data.models.api.LoginResponse;
import com.globeandi.toeat.data.models.api.LogoutResponse;
import com.globeandi.toeat.data.models.api.RegisterResponse;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.data.models.api.TripHistoryResponse;
import com.globeandi.toeat.data.models.api.TripResponse;
import com.globeandi.toeat.data.models.api.UpcomingTripsResponse;
import com.globeandi.toeat.data.models.api.VoteResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API access point
 */
public interface ToeatService {
    @Multipart
    @POST("api/register")
// http://domain.com/api/
    Single<RegisterResponse> register(@Part("name") RequestBody name,
                                      @Part("email") RequestBody email,
                                      @Part("password") RequestBody password,
                                      @Part("description") RequestBody description,
                                      @Part MultipartBody.Part image);

    //request login endpoint
    @POST("api/login")
    Single<LoginResponse> login(@Body LoginRequest.ServerLoginRequest loginRequest);

    //request logout
    @POST("api/logout")
    Single<LogoutResponse> logout();

    //request to create group
    @FormUrlEncoded
    @POST("api/user/group")
    Single<GroupResponse> createGroup(@Field("name")String name, @Field("description") String description);

    //request to abandon/delete group
    @DELETE("api/user/group/{id}")
    Single<GroupResponse> deleteGroup(@Path("id") Long groupId);

    //update group from the login user that is admin specific group
    @PUT("api/user/group/{id}")
    Single<GroupResponse> updateGroup(@Path("id") Long groupId);

    //get user all groups
    @GET("api/user/groups")
    Single<GroupResponse> getUserGroups();

    //join group
    @POST("api/group/{id}/join")
    Single<GroupResponse> joinGroup(@Path("id") Long groupId);

    //***** trips ******
    @POST("api/group/{id}/trip")
    Single<TripResponse> createTrip(@Path("id") Long groupId, @Body TripResponse.Trip trip);

    @GET("api/group/{id}/trip")
    Single<TripResponse> getActiveTrip(@Path("id") Long groupId);

    @GET("api/user/upcomingtrips")
    Single<UpcomingTripsResponse> getUpcomingTrips();

    @GET("api/user/triphistory")
    Single<TripHistoryResponse> getHistoryTrips();

    //join trip
    @POST("api/group/{id}/trip/{tripId}/join")
    Single<TripResponse> joinTrip(@Path("id") Long groupId, @Path("tripId")Long tripId);

    //un-join trip
    @DELETE("api/group/{id}/trip/{tripId}/unjoin")
    Single<TripResponse> unJoinTrip(@Path("id") Long groupId, @Path("tripId")Long tripId);

    @PUT("api/trip/{id}/completed")
    Single<TripResponse> completeTrip(@Path("id") Long id);

    //****** Vote ******
    @FormUrlEncoded
    @PUT("api/group/{id}/trip/{tripId}/vote")
    Single<VoteResponse> updatePlaceVote(@Path("id") Long groupId, @Path("tripId") Long tripId,@Field("voted_place_id") Long placeId);

    @GET("api/trip/{id}/results")
    Single<VoteResponse> getResults(@Path("id") Long tripId);

    //****** Search *******
    /*
    Use observables when using continues stream of events
     */
    //search user
    @GET("api/group/{id}/search/user")
    Single<SearchResponse> searchUser(@Path("id") Long groupId,@Query("username")String searchUsernameText);

    //for instant search
    @GET("api/search/username")
    Observable<SearchResponse> searchUserByUsername(@Query("search")String searchText);

    //*** Invite *****
    //send invitation
    @FormUrlEncoded
    @POST("api/invite/send")
    Single<InviteResponse>inviteUser(@Field("groupId") Long groupId,@Field("userId") Long userId);

    //delete invitation
    @DELETE("api/group/{groupId}/user/{userId}/invite/delete")
    Single<InviteResponse>deleteInvite(@Path("groupId") Long groupId,@Path("userId") Long userId);

    //get user invitations
    @GET("api/user/invites")
    Single<InviteResponse>getGroupInvites();



}
