package com.ohdroid.zbmaster.application.di;

import android.app.Activity;
import android.content.Context;

import com.ohdroid.zbmaster.application.data.DataManager;
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenter;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenterImp;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.presenter.imp.LoginPresenterImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ohdroid on 2016/3/22.
 */
@Module
public class ActivityModule extends PerActivityModule {
    public ActivityModule(Activity activity) {
        super(activity);
    }

    //=======================login 模块 start======================================
    @Provides
    @PerActivity
    public LoginPresenter provideLoginPresenter(DataManager dataManager) {
        return new LoginPresenterImp(provideContext(), dataManager);
    }

    //=======================login 模块 over======================================


    //=======================face sync 模块 start======================================
    @Provides
    @PerActivity
    public FaceSyncPresenter provideFaceSyncPresenter(@PerActivity Activity activity,DataManager dataManager) {
        return new FaceSyncPresenterImp(activity,dataManager);
    }


    //=======================face sync 模块 over=======================================


}
