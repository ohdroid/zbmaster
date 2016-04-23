package com.ohdroid.zbmaster.application.di;

import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.facesync.FaceSyncFragment;
import com.ohdroid.zbmaster.facesync.presenter.FaceSyncPresenter;
import com.ohdroid.zbmaster.homepage.HomePageActivity;
import com.ohdroid.zbmaster.homepage.areaface.presenter.AreaFacePresenter;
import com.ohdroid.zbmaster.homepage.areaface.view.fragment.AreaFaceDetailFragment;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieCommentPresenter;
import com.ohdroid.zbmaster.homepage.areamovie.presenter.MovieListPresenter;
import com.ohdroid.zbmaster.homepage.areamovie.view.fragment.AreaMovieFragment;
import com.ohdroid.zbmaster.homepage.areamovie.view.fragment.MovieDetailFragment;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.view.LoginFragment;

import dagger.Component;

/**
 * Created by ohdroid on 2016/3/22.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    //==============================login 模块==================================
    void inject(LoginFragment loginFragment);

    //提供登陆presenter
    LoginPresenter loginPresenter();


    //==============================face sync 模块==================================
    void inject(FaceSyncFragment faceSyncFragment);

    FaceSyncPresenter faceSyncPresenter();


    //==============================home page 模块==================================
//    void inject(HomePageActivity homePageActivity);

    //==============================face area 模块==================================
    void inject(AreaFaceDetailFragment faceDetailFragment);

    AreaFacePresenter faceAreaPresenter();


    //==============================movie info 模块==========================================
    void inject(AreaMovieFragment fragment);

    void inject(MovieDetailFragment fragment);

    MovieListPresenter movieListPresenter();

    MovieCommentPresenter movieCommentPresenter();

}
