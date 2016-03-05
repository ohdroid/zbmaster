package com.ohdroid.zbmaster.application;

import android.app.Application;

import com.ohdroid.zbmaster.BuildConfig;
import com.ohdroid.zbmaster.di.ApplicationComponent;
import com.ohdroid.zbmaster.di.ApplicationModule;
import com.ohdroid.zbmaster.di.DaggerApplicationComponent;

import cn.bmob.v3.Bmob;

/**
 * Created by ohdroid on 2016/2/28.
 */
public class BaseApplication extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, BuildConfig.BMOB_APP_KEY);
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }


    public ApplicationComponent getApplicationComponent() {
        return appComponent;
    }
}
