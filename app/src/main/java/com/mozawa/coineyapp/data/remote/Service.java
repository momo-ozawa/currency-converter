package com.mozawa.coineyapp.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface Service {

    String BASE_URL = "https://api.flickr.com/services/feeds/";

//    @GET("photos_public.gne?format=json&nojsoncallback=1")
//    Observable<FlickrFeed> getFlickrFeed(@Query("tags") String tags, @Query("tagmode") String mode);

    /******* Helper class that sets up a new service *******/
    class Creator {

        public static Service newService() {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(Service.class);
        }
    }

}
