package com.ohdroid.zbmaster.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ohdroid.zbmaster.BuildConfig;
import com.ohdroid.zbmaster.application.di.ApplicationComponent;
import com.ohdroid.zbmaster.application.di.ApplicationModule;
import com.ohdroid.zbmaster.application.di.DaggerApplicationComponent;
import com.ohdroid.zbmaster.homepage.areaface.model.FaceInfo;
import com.tencent.tauth.Tencent;

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
        Fresco.initialize(this);
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return appComponent;
    }
}
