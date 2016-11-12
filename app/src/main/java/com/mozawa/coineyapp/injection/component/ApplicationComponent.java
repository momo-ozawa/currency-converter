package com.mozawa.coineyapp.injection.component;

import android.app.Application;
import android.content.Context;

import com.mozawa.coineyapp.data.DataManager;
import com.mozawa.coineyapp.data.remote.CoineyService;
import com.mozawa.coineyapp.injection.ApplicationContext;
import com.mozawa.coineyapp.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    CoineyService coineyService();

    DataManager dataManager();

}

