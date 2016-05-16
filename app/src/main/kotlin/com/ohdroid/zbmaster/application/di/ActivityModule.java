package com.ohdroid.zbmaster.application.di;

import android.app.Activity;

import com.ohdroid.zbmaster.application.data.DataManager;
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.application.rxbus.RxBus;
import com.ohdroid.zbmaster.facesync.data.FaceSyncManager;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenter;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenterImp;
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter;
import com.ohdroid.zbmaster.homepage.areaface.presenter.imp.AreaFacePresenterImp;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.imp.MovieCommentPresenterImp;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.imp.MovieInfoListPresenterImp;
import com.ohdroid.zbmaster.login.data.LoginManager;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.presenter.imp.LoginPresenterImp;
import com.tencent.tauth.Tencent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ohdroid on 2016/3/22.
 */
@Module
public class ActivityModule {

    protected Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideContext() {
        return activity;
    }

    //=======================login 模块======================================
    @Provides
    @PerActivity
    public LoginPresenter provideLoginPresenter(DataManager dataManager, RxBus rxBus) {
        System.out.println("==========provideLoginPresenter->" + dataManager);
        return new LoginPresenterImp(provideContext(), dataManager, rxBus);
    }


    //=======================face sync 模块 ======================================
    @Provides
    @PerActivity
    public FaceSyncPresenter provideFaceSyncPresenter(@PerActivity Activity activity, DataManager dataManager) {
        return new FaceSyncPresenterImp(activity, dataManager);
    }


    //=======================face area 模块 =======================================
    @Provides
    @PerActivity
    public AreaFacePresenter provideFaceAreaPresenter(@PerActivity Activity activity) {
        return new AreaFacePresenterImp(activity);
    }

    //=======================movie area 模块========================================
    @Provides
    @PerActivity
    public MovieListPresenter provideMovieAreaPresenter(@PerActivity Activity activity) {
        return new MovieInfoListPresenterImp(activity);
    }

    @Provides
    @PerActivity
    public MovieCommentPresenter provideMovieCommentPresenter(@PerActivity Activity activity, DataManager dataManager, RxBus rxBus) {
        return new MovieCommentPresenterImp(activity, dataManager, rxBus);
    }

}
