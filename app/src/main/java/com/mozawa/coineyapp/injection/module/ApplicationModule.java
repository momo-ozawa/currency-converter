package com.mozawa.coineyapp.injection.module;

import android.app.Application;
import android.content.Context;

import com.mozawa.coineyapp.data.remote.CoineyService;
import com.mozawa.coineyapp.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    CoineyService provideService() {
        return CoineyService.Creator.newCoineyService();
    }
}
