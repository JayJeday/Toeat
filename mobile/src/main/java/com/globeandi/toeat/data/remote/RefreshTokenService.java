package com.globeandi.toeat.data.remote;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RefreshTokenService {

    @POST("api/refresh")
    @FormUrlEncoded
    Call<ApiHeader.ProtectedApiHeader> refresh(@Field("refresh_token") String refreshToken);

}
