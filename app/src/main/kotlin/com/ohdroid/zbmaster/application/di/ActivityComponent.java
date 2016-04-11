package com.ohdroid.zbmaster.application.di;

import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.facesync.FaceSyncFragment;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenter;
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter;
import com.ohdroid.zbmaster.homepage.areaface.view.AreaFaceFragment;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.view.LoginFragment;

import javax.annotation.PreDestroy;

import dagger.Component;

/**
 * Created by ohdroid on 2016/3/22.
 */
@PerActivity
@Component(dependencies = ApplicationModule.class, modules = {ActivityModule.class})
public interface ActivityComponent extends AbstractActivityComponent {
    //==============================login 模块  start==================================
    void inject(LoginFragment loginFragment);

    //提供登陆presenter
    @PerActivity
    LoginPresenter loginPresenter();


    //==============================face sync 模块   start==================================
    void inject(FaceSyncFragment faceSyncFragment);

    @PerActivity
    FaceSyncPresenter faceSyncPresenter();

    //==============================face area 模块   start==================================
    @PerActivity
    AreaFacePresenter faceAreaPresenter();


    //==============================movie info 模块==========================================

    @PerActivity
    MovieListPresenter movieListPresenter();

}
