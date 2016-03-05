package com.ohdroid.zbmaster.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ohdroid.zbmaster.base.view.BaseFragment;
import com.ohdroid.zbmaster.di.AppModule;
import com.ohdroid.zbmaster.di.PerActivityModule;
import com.ohdroid.zbmaster.login.di.DaggerLoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginModule;
import com.ohdroid.zbmaster.login.presenter.LoginPresenter;

import javax.inject.Inject;

/**
 * Created by ohdroid on 2016/3/5.*
 */
public class JLoginFragment extends BaseFragment {

    protected LoginActivityComponent component;

    @Inject
    protected LoginPresenter loginPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        component = DaggerLoginActivityComponent.builder()
                .appModule(new AppModule(context.getApplicationContext()))
//                .perActivityModule(new PerActivityModule(getActivity()))
                .loginModule(new LoginModule(getActivity()))
                .build();

    }
}
