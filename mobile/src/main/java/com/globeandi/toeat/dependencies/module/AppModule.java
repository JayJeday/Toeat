package com.globeandi.toeat.dependencies.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.globeandi.toeat.data.AppDataHolder;
import com.globeandi.toeat.data.AppDataManager;
import com.globeandi.toeat.data.DataManager;
import com.globeandi.toeat.data.local.db.AppDatabase;
import com.globeandi.toeat.data.local.db.AppDbHelper;
import com.globeandi.toeat.data.local.db.DbHelper;
import com.globeandi.toeat.data.local.prefs.AppPreferencesHelper;
import com.globeandi.toeat.data.local.prefs.PreferencesHelper;
import com.globeandi.toeat.data.remote.ApiHeader;
import com.globeandi.toeat.data.remote.ApiHelper;
import com.globeandi.toeat.data.remote.AppApiHelper;

import com.globeandi.toeat.data.remote.AuthAuthenticator;
import com.globeandi.toeat.data.remote.AuthInterceptor;
import com.globeandi.toeat.data.remote.RefreshTokenService;
import com.globeandi.toeat.data.remote.RetrofitBuilder;

import com.globeandi.toeat.data.remote.ToeatService;
import com.globeandi.toeat.dependencies.AuthRetrofit;
import com.globeandi.toeat.dependencies.DatabaseInfo;
import com.globeandi.toeat.dependencies.PreferenceInfo;
import com.globeandi.toeat.util.AppConstants;
import com.globeandi.toeat.util.BooleanDeserializer;
import com.globeandi.toeat.util.rx.AppSchedulerProvider;
import com.globeandi.toeat.util.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by jay on 3/20/2018.
 * <p>
 * Service Locator
 *
 * @provides ->  is the method in charge of providing the instance
 * @singleton -> annotation also signals to the Dagger compiler that the instance should be created only once in the application
 * <p>
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    //database

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                //for testing purposes
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    //main repository

    @Singleton
    @Provides
    DataManager provideDataManager(AppDataManager appDataManger) {
        return appDataManger;
    }

    //shared preferences
    @Provides
    @Singleton
    PreferencesHelper providesPreferencesHelper(AppPreferencesHelper appPreferenceHelper) {
        return appPreferenceHelper;
    }

    @Provides
    @Singleton
    AppDataHolder provideAppDataHolder(AppApiHelper appApiHelper) {
        return new AppDataHolder();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitForSyncCalls(Gson gson, @Named("NoAuth") OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(AppConstants.DEVELOPMENT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Named("NoAuth")
    @Provides
    @Singleton
    OkHttpClient provideAuthClientNoAuth(Cache cache) {
        return new OkHttpClient().newBuilder()
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    RefreshTokenService provideRefreshService(Retrofit retrofit) {
        return retrofit.create(RefreshTokenService.class);
    }


    @AuthRetrofit
    @Provides
    @Singleton
    Retrofit provideAuthRetrofit(Gson gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory,@Named("outhClient") OkHttpClient authClient) {
        return new Retrofit.Builder().baseUrl(AppConstants.DEVELOPMENT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(authClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }
    @Named("outhClient")
    @Provides
    OkHttpClient provideAuthClient(AuthInterceptor authInterceptor, AuthAuthenticator authAuthenticator,Cache cache) {
        return new OkHttpClient().newBuilder()
                .authenticator(authAuthenticator)
                .addInterceptor(authInterceptor)
                .cache(cache)
                //for testing network purposes
//               .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    ToeatService provideApiService(@AuthRetrofit Retrofit retrofit) {
        return retrofit.create(ToeatService.class);
    }

    @Provides
    RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory(SchedulerProvider schedulerProvider) {
        return RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io());
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(boolean.class, new BooleanDeserializer())
                .excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    SchedulerProvider providesSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }
}
