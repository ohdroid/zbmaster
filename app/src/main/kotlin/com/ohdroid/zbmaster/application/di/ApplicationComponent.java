package com.ohdroid.zbmaster.application.di;

import android.content.Context;

import com.ohdroid.zbmaster.application.data.DataManager;
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;
import com.ohdroid.zbmaster.application.rxbus.RxBus;
import com.ohdroid.zbmaster.facesync.data.FaceSyncManager;
import com.ohdroid.zbmaster.application.data.ShareHelper;
import com.ohdroid.zbmaster.login.data.LoginManager;
import com.tencent.tauth.Tencent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/27.
 */
//@Component(modules = AppModule.class)
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ForApplication
    Context getApplicationContext();//提供application 且需要@ForApplication过滤


    DataManager dataManger();

    LoginManager loginManager();

    FaceSyncManager faceSyncManager();

    RxBus rxBus();

    Tencent tencentManager();

    ShareHelper shareHelper();
}
