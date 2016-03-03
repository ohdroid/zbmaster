package com.ohdroid.zbmaster.login.view;

import android.os.Bundle;

import com.ohdroid.zbmaster.base.view.BaseActivity;
import com.ohdroid.zbmaster.di.AppModule;
import com.ohdroid.zbmaster.di.PerActivityModule;
import com.ohdroid.zbmaster.di.exannotation.PerActivity;
import com.ohdroid.zbmaster.login.di.DaggerLoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginActivityModule;
import com.ohdroid.zbmaster.login.di.LoginModule;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;

import javax.inject.Inject;

/**
 * Created by ohdroid on 2016/2/28.
 */
public class JLoginActivity extends BaseActivity {
    protected LoginActivityComponent activityComponent;

    @Inject
    @PerActivity
    protected LoginPresenter mPresenter;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerLoginActivityComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .perActivityModule(new PerActivityModule(this))
                .loginModule(new LoginModule())
                .build();
    }
}
