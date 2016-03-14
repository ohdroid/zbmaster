package com.ohdroid.zbmaster.login.di;

import android.app.Activity;
import android.content.Context;

import com.ohdroid.zbmaster.application.data.DataManager;
import com.ohdroid.zbmaster.application.di.PerActivityModule;
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;
import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;
import com.ohdroid.zbmaster.login.presenter.imp.LoginPresenterImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ohdroid on 2016/2/28.
 */
@Module
public class LoginModule extends PerActivityModule {

    public LoginModule(Activity activity) {
        super(activity);
    }

    @Provides
    @PerActivity
    public LoginPresenter provideLoginPresenter(@ForApplication Context context, DataManager dataManager) {
        return new LoginPresenterImp(context, dataManager);
    }
}
