package com.ohdroid.zbmaster.login.di;

import com.ohdroid.zbmaster.di.AbstractActivityComponent;
import com.ohdroid.zbmaster.di.AppModule;
import com.ohdroid.zbmaster.di.PerActivityModule;
import com.ohdroid.zbmaster.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.presenter.imp.LoginPresenterImp;
import com.ohdroid.zbmaster.login.view.LoginActivity;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/28.
 */
@PerActivity
//@Component(dependencies = AppModule.class, modules = {PerActivityModule.class, LoginActivityModule.class})
@Component(dependencies = AppModule.class, modules = {PerActivityModule.class, LoginModule.class})
public interface LoginActivityComponent extends AbstractActivityComponent {

    void inject(LoginActivity loginActivity);//注入LoginActivity需要的可注入对象

    //    TODO 提供登陆presenter
    @PerActivity
    LoginPresenter loginPresenter();


}
