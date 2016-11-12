package com.mozawa.coineyapp.data.remote;

import com.mozawa.coineyapp.data.model.ExchangeResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface CoineyService {

    String BASE_URL = "https://android.coiney.com:1337/";

    @GET("exchange_rates")
    Observable<ExchangeResponse> getExchangeRates();

    /******* Helper class that sets up a new service *******/
    class Creator {

        public static CoineyService newCoineyService() {

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

            return retrofit.create(CoineyService.class);
        }
    }

}
