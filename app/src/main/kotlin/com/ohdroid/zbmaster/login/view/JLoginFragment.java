package com.ohdroid.zbmaster.login.view;

import android.content.Context;

import com.ohdroid.zbmaster.base.view.BaseFragment;
import com.ohdroid.zbmaster.di.AppModule;
import com.ohdroid.zbmaster.login.di.DaggerLoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginModule;

/**
 * Created by ohdroid on 2016/3/5.*
 */
public class JLoginFragment extends BaseFragment {

    protected LoginActivityComponent component;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        component = DaggerLoginActivityComponent.builder()
                .appModule(new AppModule(context.getApplicationContext()))
                .loginModule(new LoginModule(getActivity()))
                .build();

    }
}
