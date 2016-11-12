package com.mozawa.coineyapp;

import android.app.Application;
import android.content.Context;

import com.mozawa.coineyapp.injection.component.ApplicationComponent;
import com.mozawa.coineyapp.injection.component.DaggerApplicationComponent;
import com.mozawa.coineyapp.injection.module.ApplicationModule;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        applicationComponent = applicationComponent;
    }

}
