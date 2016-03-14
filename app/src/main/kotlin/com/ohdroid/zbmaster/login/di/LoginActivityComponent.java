package com.ohdroid.zbmaster.login.di;

import com.ohdroid.zbmaster.application.di.AbstractActivityComponent;
import com.ohdroid.zbmaster.application.di.ApplicationModule;
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.view.LoginFragment;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/28.
 */
@PerActivity
//@Component(dependencies = AppModule.class, modules = {PerActivityModule.class, LoginActivityModule.class})
@Component(dependencies = ApplicationModule.class, modules = {LoginModule.class})
public interface LoginActivityComponent extends AbstractActivityComponent {

//    void inject(LoginActivity loginActivity);//注入LoginActivity需要的可注入对象

    void inject(LoginFragment loginFragment);

    //提供登陆presenter
    @PerActivity
    LoginPresenter loginPresenter();


}
