package com.globeandi.toeat.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.globeandi.toeat.BuildConfig;

import com.globeandi.toeat.data.AppDataManager;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static final String BASE_URL = "http://10.0.2.2:80";
    private static final String BASE_URL2 = "https://api.toeat.com/";

    //places url
    public static final String GOOGLE_PLACES_BASE_URL_API = "https://maps.googleapis.com/maps/";


    public static final OkHttpClient client = buildClient();

    //represent the retrofit instance that we need
    private static Retrofit retrofit = buildRetrofit(client);


    private static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection", "close");

                        request = builder.build();

                        return chain.proceed(request);
                    }
                });

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }

    /*
    A more flexible approach to instantiate retrofit
    Evaluate this against the dagger approach
     */
    private static Retrofit buildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .build();
    }

    /*
    In case we need to specify more than one service
     */
    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
