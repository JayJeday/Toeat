package com.globeandi.toeat.dependencies.module;

import com.globeandi.toeat.data.remote.GooglePlacesService;
import com.globeandi.toeat.data.remote.RetrofitBuilder;
import com.globeandi.toeat.data.remote.ToeatService;
import com.globeandi.toeat.dependencies.AuthRetrofit;
import com.globeandi.toeat.dependencies.PlacesRetrofit;
import com.globeandi.toeat.util.AppConstants;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkAppModule {

    /*
    Retrofit for Google Place call
     */
    @PlacesRetrofit
    @Provides
    @Singleton
    Retrofit provideRetrofitPlacesServices(Gson gson, @Named("NoAuth") OkHttpClient client,RxJava2CallAdapterFactory rxJava2CallAdapterFactory){
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstants.GOOGLE_PLACES_BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

    @Provides
    @Singleton
    GooglePlacesService providePlacesApiService(@PlacesRetrofit Retrofit retrofit) {
        return retrofit.create(GooglePlacesService.class);
    }
}
