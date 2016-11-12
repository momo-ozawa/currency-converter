package com.mozawa.coineyapp.injection.component;

import com.mozawa.coineyapp.injection.PerActivity;
import com.mozawa.coineyapp.injection.module.ActivityModule;
import com.mozawa.coineyapp.ui.base.BaseActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

}
